import java.io.*;
import java.util.*;
import java.util.concurrent.*;
public class MySort {
		
	public static void main(String[] args) throws InterruptedException, IOException,FileNotFoundException{
		
		
		if (args[0] == null || args[0].trim().isEmpty()) {
	        System.out.println("You need to specify input file along with its path !");
	        return;
	    } 
		if (args[1] == null|| args[1].trim().isEmpty()){
			System.out.println("Please specify number of threads to be used");
			return;
		}
		/*if (args[2] == null|| args[2].trim().isEmpty()){
			System.out.println("Please specify number of temp files to be created");
			return;
		}*/
		//input file name path
		String inFile = args[0];
		//number of threads
		int numThreads = Integer.parseInt(args[1]);
		System.out.println("Number of threads used:" + numThreads);
		//decide on number of temp files
		//int tempFilescount = Integer.parseInt(args[2]);
		System.out.println("Input file name is:"+ inFile);
		
		long availableRAM=Runtime.getRuntime().freeMemory();
	
		File fr = new File(inFile);
		long fileLength = fr.length();
        long chunkLength = fileLength / 1024 ;
        /* Total amount of free memory available to the JVM */
        long counter2;int counter;
        
        System.out.println("Currently,free RAM is:" + availableRAM);
        
//        blocksize = (blocksize < freeMem/2) ? freeMem/2 : -1;  
//        if(blocksize == -1) {
//        	System.err.println("Insufficient Memory");
//        }
        if( chunkLength < availableRAM/2)
        	chunkLength = availableRAM/2;
        else { 
        	if(chunkLength >= availableRAM) 
              System.err.println("Insufficient RAM available");
        }
	System.out.println("chunksize:" + chunkLength);
	System.out.println("1.Splitting Files into chunks");	
        long timerBegins = System.currentTimeMillis();
        //int rowCount = 1;
		
		ArrayList<String> rows = new ArrayList<String>(); //It will store all the lines and provide for each chunk
		
		try {
			BufferedReader br  = new BufferedReader(new FileReader(inFile));			
			counter = 1;
			String temp = "";
			long writecount =0;
			long readcount=0;
			//for(;temp!=null;)
			do
			//while(temp!=null)
			{
				//System.out.println("Inside for loop");
				counter2 = 0;// in bytes

				
				//do      {
					//System.out.println("Inside 2nd for loop");
				while(counter2 < chunkLength && (temp=br.readLine()) != null){
					//temp = br.readLine();
					//if(counter2 < chunkLength && temp != null) {
							rows.add(temp);
							counter2 += temp.length();
						readcount++;
							/*}
					else {
						System.out.println("else Break");
						break;}*/
				}//while(counter2 < chunkLength && temp != null);
				//System.out.println("Counter 2 value: "+counter2);
				//System.out.println("Read Count value: "+readcount);
				try {
				//	System.out.println("Creating chunk file: unsorted" + counter+".txt");
					FileWriter fwr = new FileWriter("/tmp/unsorted"+counter+".txt");
				//System.out.println("Rows size for file: unsorted"+counter+"is;"+rows.size());
				for (String str: rows) {
			            fwr.write(str);
						writecount++;
			            if(str.contains(" "))//This prevent creating a blank like at the end of the file**
			                fwr.write("\n");
			        }
				//	System.out.println("File write: "+writecount);
				//System.out.println("Reached end of try:" + counter);	
			        fwr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				counter++;
				rows.clear();
			}while(temp!=null);
			//}
			//System.out.println("Close1");
//			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		//TimeUnit.MINUTES.sleep(1);
		ThreadManager tmg = new ThreadManager();
		System.out.println("Unsorted chunks created and multithreading starts");
		tmg.Manager(timerBegins); //object declaration
		
	}	
		
}




///My Program started
//public class ThreadManager implements Runnable{
class ThreadManager implements Runnable{

	static Comparator<String> comparator;
	private File[] unSortedFiles;
	//Output file name
	//final static File outputFile = new File("output.txt"); 
	final static File outputFile = new File("/tmp/MySortedFile.out"); 
	
	//int noOfFiles;
	
	
	ThreadManager(){
		
	    
		File fname = new File("/tmp/");
	    
		unSortedFiles = fname.listFiles( new FilenameFilter() {
			@Override
	        public boolean accept(File fname, String name) {
	            return name.startsWith("unsorted")&& name.endsWith(".txt");
	        }
	    });
		
	}
	
	public void Manager(final long start) throws IOException, InterruptedException {
		//System.out.println("Inside thread master");
		int nFiles = unSortedFiles.length;
		//System.out.println("Number of unsorted files is :" + nFiles);
		
		comparator = new Comparator<String>() {
            public int compare(String s1, String s2){
                return s1.compareTo(s2);
            }
		};
		//System.out.println("Pool of Threads started");
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		//OVerriding the run function of the thread to do multi-threading
		executorService.execute(new Runnable() { //executing threads
			
		    public void run() {
				//System.out.println("Inside run instance");
		        ArrayList<String> sortedLines = null;
				int nFiles = unSortedFiles.length;
				
				//for(int i=0; i<unSortedFiles.length; i++)  
				System.out.println("Sorting of unsorted files begins");
				int i=0;
				while(i<unSortedFiles.length)	
				{
					try {	
						sortedLines = chunksReadAndSort(i);
						writeSortedChunks(sortedLines,i);		
					} catch (IOException e) {
						e.printStackTrace();
					}
					i++;
				}
				
				//Threads started for Sorting and Merging all the Sorted chunks into 1 single file
				System.out.println("Now Sorted Chunks will Sort and Merge into 1 File using Threads");
				System.out.println("\nMulti-Threading started...\n");
				
				
				//for(i=1;i<=8;i++)
				i=1;
				while(i<=4)
				{
					i++;
					ThreadManager tmg = new ThreadManager();
					//System.out.println("Thread# "+i+" started..");
					Thread t =  new Thread(tmg);
					t.start();
				}
				
				//System.out.println("\Files Sorted and Merged successfully !!");
				//System.out.println("\nProcess Completed..\n[Please find the file in the root folder as 'output.txt']");
				//long stop = System.currentTimeMillis();
				long diff = System.currentTimeMillis() - start;
				System.out.println("Merging Completed and final output saved");
				//System.out.println("\nTOTAL TIME ELAPSED: "+diff/1000+" seconds");	        
				System.out.println("Time elapsed in seconds for the sorting is: "+diff/1000);
				/*File fi = new File("output.txt");
				long fiLength = fi.length();
				System.out.println("Total outputfile length is : "+fiLength);*/	
		    }
		});
		executorService.shutdown();	
	}
	
	@Override
	public void run() {
		try {
			//System.out.println("Inside run thread before final merge of sorted files ");
			mergeSortedFiles(outputFile, comparator);
			//System.out.println("Inside run thread after final merge of sorted files ");
		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println("Issue in the native run program in the try block");
		}
	}
	
	private ArrayList<String> chunksReadAndSort(int i) throws IOException {
		
		//String fname;
		
		ArrayList<String> lines = null;
		
		//BufferedReader breader = null;
		String line = "";
		lines	= new ArrayList<String>();
		String fname = unSortedFiles[i].getName();
		//breader = new BufferedReader(new FileReader("UNSORTED_CHUNKS/"+fname));
		BufferedReader breader = new BufferedReader(new FileReader("/tmp/"+fname));
		
		ArrayList<String> StringList = null;
		//System.out.println("Before chunk read and sorted");
		while((line = breader.readLine())!=null){
			String key = line.substring(0, 10); // Extracting first 10 byte string key
			String val = line.substring(10, line.length());
			lines.add(key+val);
		}
		//System.out.println("Chunk's sort finished and we have sorted intermediate files");
		StringList = sort(lines,i); 
		//System.out.print(i +"/r"); 
		return StringList;		
	}

	
	private ArrayList<String> sort(ArrayList<String> lines, int i) {
		
		//System.out.println("Chunk's sort finished and we have sorted intermediate files");
		ArrayList<String> sortedKeys; // ArrayList containing all the Sorted Keys
		//ArrayList<String> sortedLines = null; // ArrayList containing all the Sorted Lines (keys+values)
		Hashtable<String, String> whole = new Hashtable<String, String>();
		
		String key;
		String val = null;
		int count = 0;
		
		//ArrayList<String> keys = null; // ArrayList containing all the Unsorted Keys
		ArrayList<String> keys = new ArrayList<String>(); 
		i=0;
		int length;
		//for(String ss: lines)
		//System.out.println("lines.size is : "+lines.size());
		while(i<lines.size())
		{
			//Declaring object for keys
			//key = ss.substring(0, 10);
			length = lines.get(i).length();
			key = lines.get(i).substring(0,10);
			//val = ss.substring(10,ss.length());
			val = lines.get(i).substring(10,length);
			keys.add(key);
			whole.put(key,val);
				
			count++;
			i++;
		}
		sortedKeys = mergeSort(keys);
		ArrayList<String> sortedLines = new ArrayList<String>();
		
		//for(String s1: sortedKeys)
		i=0;
		while(i<sortedKeys.size())
		{
			String value = whole.get(sortedKeys.get(i));
			sortedLines.add(sortedKeys.get(i)+" "+value);
			i++;
		}
		//System.out.println("No of sorted lines: "+sortedLines.size());
		return sortedLines;
	}

	
	public ArrayList<String> mergeSort(ArrayList<String> whole) {
		//System.out.println("MergeSort of files starts");
	    //right side of the whole array
	    ArrayList<String> right = new ArrayList<String>();
	    int center;
		//left side of the whole array
		ArrayList<String> left = new ArrayList<String>();
	    /*if (whole.size() == 1) {    
	        return whole;
	    } */
		
		//else {
		if (whole.size() != 1) {    
	        center = whole.size()/2;
	        // copy the left half of whole into the left.
	        //for (int i=0; i<center; i++) 
			int i=0;
			while(i<center)
			{
	                left.add(whole.get(i));
					i++;
	        }
	        //copy the right half of whole into the new arraylist.
			//for (int i=center; i<whole.size(); i++)
			i=center;
			while(i<whole.size())	
			{
	                right.add(whole.get(i));
				i++;
	        }
	        // Sort the left  halves of the arraylist.
	        left  = mergeSort(left);
			
			// Sort the right  halves of the arraylist.
	        right = mergeSort(right);
	 
			//System.out.println("Recursive calls to merge and mergesort finished");
	        // Merge the results back together.
	        merge(left, right, whole);
	    }
		else {
			return whole;
		}
	    return whole;
	}
	
	
	private void merge(ArrayList<String> left, ArrayList<String> right, ArrayList<String> whole) {
	    int wIndex = 0;
		//System.out.println("Inside mergeArray");
		int lIndex = 0;
	    //System.out.println("for debug1");
	    
		int rIndex = 0;
	    
	    while (lIndex < left.size() && rIndex < right.size()) {
	    	
			/*if ( (left.get(lIndex).compareTo(right.get(rIndex))) < 0) 
			{	
	            whole.set(wIndex, left.get(lIndex));
	            lIndex++;
	        } else {
	            whole.set(wIndex, right.get(rIndex));
	            rIndex++;
	        }*/
			
			if ( (left.get(lIndex).compareTo(right.get(rIndex))) >= 0) {
				whole.set(wIndex, right.get(rIndex));
	            rIndex++;
				//System.out.println("Debug2 - inside right index, right index:"+rIndex);
			}
			else{
				//System.out.println("Debug3 - inside left index");
				whole.set(wIndex, left.get(lIndex));
	            lIndex++;
				//System.out.println("Debug4 - left index counter:"+lIndex);
			}
			
	        wIndex++;
	    }
		//System.out.println("Debug5 - before comparision of array sizes");
	    ArrayList<String> rest;
	    int restIndex;
	    /*
	    if (lIndex >= left.size()) { 
	        rest = right; // The left ArrayList has been use up...
	        restIndex = rIndex;
	    } else {
	        rest = left; // The right ArrayList has been used up...
	        restIndex = lIndex;
	    }*/
	 	
		if (lIndex < left.size()) { 
			rest = left; // The right ArrayList has been used up...
	        restIndex = lIndex;
	    } else {
	        rest = right; // The left ArrayList has been use up...
	        restIndex = rIndex;
	    }
	 //System.out.println("Debug6 - after comparision of array sizes");
		
		
		
	    // Copy the rest of whichever ArrayList (left or right) was not used up.
		//for (int i=restIndex; i<rest.size(); i++) {
		int i = restIndex;
	    while(i<rest.size())
		{
	        whole.set(wIndex, rest.get(i));
	        wIndex++;
			//loop counter	
			i++;
	    }
		//System.out.println("Debug6 - wholIndex value"+ wIndex);
		
	}
	
	
	private void writeSortedChunks(ArrayList<String> sortedLines, int i) throws IOException {
		
		
		String folder = "/tmp/";
		
		
		//File tmpfile = new File(folder+(i+1)+".txt");
		//String filevalue = folder + "final_sorted" + (i+1) + ".txt");
		File tmpfile = new File(folder+"final_sorted"+(i+1)+".txt");
		//System.out.println("WSC1.2 - filevalue:"+filevalue);
		BufferedWriter fbw = new BufferedWriter(new FileWriter(tmpfile));
		//for(String str: sortedLines)
		int j=0;
		while(j<sortedLines.size())	
		{
			//fbw.write(str+"\r\n");
			//fbw.write(str);
			fbw.write(sortedLines.get(j));
            fbw.newLine();
			j++;
		}
		//System.out.println("WSC1.3 - j value:"+j);
		fbw.close();
	}
	
	
	public static int mergeSortedFiles(File outputfile, final Comparator<String> cmp) throws IOException {
		
		//System.out.println("MSF1.1 - started");
		List<File> files = new ArrayList<File>();
		//File folder = new File ("SORTED_CHUNKS/");
		//File[] allFiles = folder.listFiles();
		
		//System.out.println("MSF1.2 - collecting filenames from tmp folder");
		File folder = new File ("/tmp/");
		File[] allFiles = folder.listFiles( new FilenameFilter() {
			@Override
	        public boolean accept(File f, String name) {
	            return name.startsWith("final_sorted")&& name.endsWith(".txt");
	        }
	    });
		
		//System.out.println("MSF1.3");
		//for(File file: allFiles)
		int j=0;
		while(j< allFiles.length)
		{
			
			/*if(file.getName() != ".DS_Store" && file.isFile()){ 
				files.add(file);
			}*/
			
			if(allFiles[j].getName() != ".DS_Store" && allFiles[j].isFile()){ 
				files.add(allFiles[j]);
			}
			
			j++;
		}
		//System.out.println("MSF1.4 - After getting file names");
        PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<BinaryFileBuffer>(11, 
            new Comparator<BinaryFileBuffer>() {
              public int compare(BinaryFileBuffer i, BinaryFileBuffer j) {
                return cmp.compare(i.peek(), j.peek());
              }
            }
        );
        
        //for (File f : files)
		j=0;
		while(j< files.size())
		{
            BinaryFileBuffer bfb = new BinaryFileBuffer(files.get(j));
            pq.add(bfb);	    
			j++;
        }
        
        BufferedWriter fbw = new BufferedWriter(new FileWriter(outputfile));
        int rowcounter = 0;
        try {
            //while(pq.size()>0) 
			do
			{
                BinaryFileBuffer bfb = pq.poll();
                //String r = bfb.pop();
                //fbw.write(r);
				
				fbw.write(bfb.pop());
				//fbw.write(r+"\r\n");
                fbw.newLine();
                ++rowcounter;
                /*if(bfb.empty()) {
                    //bfb.fbr.close();
					bfb.buffRead.close();
                 // Deleting old chunks to save space, because we dont need them anymore
                    //bfb.originalfile.delete(); 
                } else {
                    pq.add(bfb); // add it back
					}
					*/
				if(!bfb.empty()) {
                    pq.add(bfb); // add it back*/
                } else {
					//bfb.fbr.close();
					bfb.buffRead.close();
                 // Deleting old chunks to save space, because we dont need them anymore
                    //bfb.originalfile.delete(); 
				}
                
            }while(pq.size()>0);
			
        } finally { 
            fbw.close();
            for(BinaryFileBuffer bfb : pq ) bfb.close();
        }
        return rowcounter;
	}	
}


class BinaryFileBuffer  {
    
    
	public BufferedReader buffRead;
    //public File actualFile;
    
	private String cString;
	//The below is for validating whether its emppty or not
	//private int tnumber;
    private boolean bvalue;
    //public static int maxsize = 10240;
	
    public BinaryFileBuffer(File fname) throws IOException {
        //buffRead = new BufferedReader(new FileReader(fname), maxsize);
		buffRead = new BufferedReader(new FileReader(fname), 10240);
		//actualFile = fname;
        reload();
    }
     
    public boolean empty() {
        return bvalue;
    }
     
    private void reload() throws IOException {
        try {
        	
        	this.cString = buffRead.readLine();
        	bvalue = (this.cString==null) ? true : false; 
        	if(this.cString == null) cString = null;
			
      } catch(EOFException endoffile) {
        bvalue = true;
        cString = null;
      }
    }
     
    public void close() throws IOException {
        buffRead.close();
    }
     
    public String peek() {
        String x = empty() ? null : cString.toString();
        return x;
		//if(empty()) return null;
        //return cString.toString();
    }
    public String pop() throws IOException {
      String temp = peek();
        reload();
      return temp;
    }
}
