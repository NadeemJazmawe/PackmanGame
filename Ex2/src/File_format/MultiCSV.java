package File_format;

import java.io.File;
//we have used a function from the website:
//https://www.mkyong.com/java/search-directories-recursively-for-file-in-java/

/**
 * @author 207392283 and 314638867
 * 
 * This Class convert files from Csv to Kml .
 *
 */
public class MultiCSV {
	private String path;
	File file;

	
	/**
	 * @param path the name of the file that we want to search in it and convert the CSV files in it to KMl .
	 * 
	 * Constructor for our class.
	 */
	public MultiCSV(String path) {
		this.path=path;
		file=new File(path);
	}
	public static void main(String[] args) {
		MultiCSV dir = new MultiCSV("C:\\Users\\Nadeem\\Desktop");
		dir.search(dir.file);
	}
	
	
	/**
	 * @param s the name of the file that we want to convert .
	 * this function well convert the file from CSV to KMl , by using "Csv2kml" Class .
	 */
	public void convert(String s) {
		Csv2kml csv=new Csv2kml(s);
		csv.write();
	}
	
	
	/**
	 * @param file the name of the file that we want to work on it
	 * 
	 * this function search in the file of CSv files that we want to convert , 
	 * if it find any file that need to convert ,
	 * it well send hem to "convert" function in this class to convert it .
	 */
	private void search(File file) {

		if (file.isDirectory()) {
			System.out.println("Searching directory ... " + file.getAbsoluteFile());
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp);
					} else {
						if (temp.getName().toLowerCase().contains(".csv")) {			
							try{
								convert(temp.getAbsoluteFile().toString());
								System.out.println(temp.getName()+" ....Has been converted to kml....");
							}
							catch(Exception ex) {
								System.out.println(
										"Error Converting csv file '" 
												+ temp.getName() + "'");                  
							}
						}

					}
				}

			}
			else {
				System.out.println(file.getAbsoluteFile() + "Permission Denied");
			}
		}

	}
}