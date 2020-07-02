package DataManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import Entities.Course;
import Entities.Student;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

public class DataManager {
    private String Data_Path;
    
    public DataManager(){
        Data_Path = "src/main/resources/data/data.xls";
    }

    public DataManager(String path) {
    	Data_Path = path;
    }
    
    public List<Student> ReadStudentsFromExcel(){
        List<Student> studentsOutOfFile = new ArrayList<>();
        try {
        	FileInputStream data = new FileInputStream(new File(Data_Path));
        	System.out.println("Accesing File");
        	HSSFWorkbook workbook = new HSSFWorkbook(data);
        	HSSFSheet studentsSheet = workbook.getSheet("Students");
        	Iterator<Row> rowIterator = studentsSheet.iterator();
        	rowIterator.next();
            Student.STUDENT_ID_COUNT =0;
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                Student student = new Student();
                student.STUDENT_ID = (int)cellIterator.next().getNumericCellValue();
                student.setFirstName(cellIterator.next().getStringCellValue());
                student.setLastName(cellIterator.next().getStringCellValue());
                student.setDocumentNumber(cellIterator.next().getStringCellValue());
                student.setAddress(cellIterator.next().getStringCellValue());
                student.setStudentStatus(cellIterator.next().getStringCellValue());
                student.setCredits((int)cellIterator.next().getNumericCellValue());
                studentsOutOfFile.add(student);
            }
            data.close();
            System.out.println("Student data successfully loaded\n");
        }catch (Exception e) {
            System.out.println("Something went wrong while loading students data");
            e.printStackTrace();
		}
        return studentsOutOfFile;
    }
    
    public void WriteStudentsToExcel(List<Student> studentsToExcel){ 
    	File file = new File(Data_Path); 
 
        if (file.isFile() && file.exists()) { 
            System.out.println("Saving students"); 
            try {
            	FileInputStream fip = new FileInputStream(file);
                HSSFWorkbook workbook = new HSSFWorkbook(fip); 
                HSSFSheet sheet = workbook.getSheet("Students");
                if(sheet==null) {
                	sheet = workbook.createSheet("Students");
                }else {
                	int index = workbook.getSheetIndex(sheet);
                	workbook.removeSheetAt(index);
                	sheet = workbook.createSheet("Students");
                }
                Map<String, Object[]> data = new TreeMap<String, Object[]>();
                data.put("1", new Object[] {"ID","First name","Last name","Document number","Address","Status","Credits"});
                for(Student student:studentsToExcel) {
                	data.put(""+(student.STUDENT_ID+1), new Object[] {student.STUDENT_ID,student.getFirstName(),student.getLastName(),student.getDocumentNumber(),student.getAddress(),student.getStudentStatus(),student.getCredits()});
                }
                
                Set<String> keyset = data.keySet(); 
                int rownum = 0; 
                for (String key : keyset) { 
                    Row row = sheet.createRow(rownum++); 
                    Object[] objArr = data.get(key); 
                    int cellnum = 0; 
                    for (Object obj : objArr) { 
                        Cell cell = row.createCell(cellnum++); 
                        if (obj instanceof String) 
                            cell.setCellValue((String)obj); 
                        else if (obj instanceof Integer) 
                            cell.setCellValue((Integer)obj); 
                    } 
                }
                
                FileOutputStream out = new FileOutputStream(new File(Data_Path)); 
                workbook.write(out); 
                out.close();
                System.out.println("Students saved successfully");
            }catch (Exception e) {
				e.printStackTrace();
			}
        } 
        else { 
            System.out.println("data.xls either does not exist or can't open"); 
        } 
    }
    
    public List<Course> ReadCoursesFromExcel(){
        List<Course> coursesOutOfFile = new ArrayList<>();
        try {
            FileInputStream data = new FileInputStream(new File(Data_Path));
            System.out.println("Accesing File");
            HSSFWorkbook workbook = new HSSFWorkbook(data);
            HSSFSheet coursesSheet = workbook.getSheet("Courses");
            Iterator<Row> rowIterator = coursesSheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                Course course = new Course();

                course.COURSE_ID = cellIterator.next().getStringCellValue();
                course.setCourseName(cellIterator.next().getStringCellValue());
                course.setDepartment(cellIterator.next().getStringCellValue());
                course.setCreditHrs((int)cellIterator.next().getNumericCellValue());
                course.setRatePerCredit(cellIterator.next().getNumericCellValue());
                coursesOutOfFile.add(course);
            }
            data.close();
            System.out.println("Courses data successfully loaded\n");
        }catch (Exception e) {
            System.out.println("Something went wrong while loading courses data");
            e.printStackTrace();
        }
        return coursesOutOfFile;
    }
    
    public void WriteCoursesToExcel(List<Course> coursesToExcel){
    	File file = new File(Data_Path); 
    	 
        if (file.isFile() && file.exists()) { 
            System.out.println("Saving courses"); 
            try {
            	FileInputStream fip = new FileInputStream(file);
                HSSFWorkbook workbook = new HSSFWorkbook(fip); 
                HSSFSheet sheet = workbook.getSheet("Courses");
                if(sheet==null) {
                	sheet = workbook.createSheet("Courses");
                }else {
                	int index = workbook.getSheetIndex(sheet);
                	workbook.removeSheetAt(index);
                	sheet = workbook.createSheet("Courses");
                }
                Map<String, Object[]> data = new TreeMap<String, Object[]>();
                data.put("1", new Object[] {"ID","Course name","Department","Credit hours","Rate per credit"});
                int index = 2;
                for(Course course:coursesToExcel) {
                	data.put(""+index++, new Object[] {course.COURSE_ID,course.getCourseName(),course.getDepartment(),course.getCreditHrs(),course.getRatePerCredit()});
                }
                
                Set<String> keyset = data.keySet(); 
                int rownum = 0; 
                for (String key : keyset) { 
                    Row row = sheet.createRow(rownum++); 
                    Object[] objArr = data.get(key); 
                    int cellnum = 0; 
                    for (Object obj : objArr) { 
                        Cell cell = row.createCell(cellnum++); 
                        if (obj instanceof String) 
                            cell.setCellValue((String)obj); 
                        else if (obj instanceof Integer) 
                            cell.setCellValue((Integer)obj);
                        else if (obj instanceof Double)
                        	cell.setCellValue((Double)obj);
                    } 
                }
                
                FileOutputStream out = new FileOutputStream(new File(Data_Path)); 
                workbook.write(out); 
                out.close();   
                System.out.println("Courses saved successfully");
            }catch (Exception e) {
				e.printStackTrace();
			}
        } 
        else { 
            System.out.println("data.xls either does not exist or can't open"); 
        } 
    }

    public HashMap<Integer,List<String>> ReadStudentCoursesFromExcel(){
        HashMap<Integer,List<String>> recoveredStudentCourses = new HashMap<>();
        try {
            FileInputStream data = new FileInputStream(new File(Data_Path));
            System.out.println("Accesing File");
            HSSFWorkbook workbook = new HSSFWorkbook(data);
            HSSFSheet coursesSheet = workbook.getSheet("StudentsCourses");
            Iterator<Row> rowIterator = coursesSheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                int studentID = (int)cellIterator.next().getNumericCellValue();
                recoveredStudentCourses.put(studentID,new ArrayList<String>());
                while(cellIterator.hasNext()){
                    recoveredStudentCourses.get(studentID).add(cellIterator.next().getStringCellValue());
                }
            }
            data.close();
            System.out.println("StudentCourses data successfully loaded\n");
        }catch (Exception e) {
            System.out.println("Something went wrong while loading student-courses data");
            e.printStackTrace();
        }
        return recoveredStudentCourses;
    }

    public void WriteStudentCoursesToExcel(HashMap<Integer,List<String>> studentCoursesToExcel){
        File file = new File(Data_Path);

        if (file.isFile() && file.exists()) {
            System.out.println("Saving student-courses");
            try {
                FileInputStream fip = new FileInputStream(file);
                HSSFWorkbook workbook = new HSSFWorkbook(fip);
                HSSFSheet sheet = workbook.getSheet("StudentsCourses");
                if(sheet==null) {
                    sheet = workbook.createSheet("StudentsCourses");
                }else {
                    int index = workbook.getSheetIndex(sheet);
                    workbook.removeSheetAt(index);
                    sheet = workbook.createSheet("StudentsCourses");
                }
                Map<String, Object[]> data = new TreeMap<String, Object[]>();
                data.put("1", new Object[] {"Student ID", "Courses IDs"});
                int index = 2;
                for(int student_ID: studentCoursesToExcel.keySet()) {
                    Object[] row = new Object[studentCoursesToExcel.get(student_ID).size()+1];
                    row[0]=student_ID;
                    int i=1;
                    for(String course_ID: studentCoursesToExcel.get(student_ID)){
                        row[i]= course_ID;
                        i++;
                    }
                    data.put(""+index++, row);
                }

                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset) {
                    Row row = sheet.createRow(rownum++);
                    Object[] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        if (obj instanceof String)
                            cell.setCellValue((String)obj);
                        else if (obj instanceof Integer)
                            cell.setCellValue((Integer)obj);
                        else if (obj instanceof Double)
                            cell.setCellValue((Double)obj);
                    }
                }

                FileOutputStream out = new FileOutputStream(new File(Data_Path));
                workbook.write(out);
                out.close();
                System.out.println("Student courses saved successfully");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("data.xls either does not exist or can't open");
        }
    }

}
