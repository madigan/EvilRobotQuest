import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** This parser is used to read files and look for */
public class jParser
{
	private static Scanner input;
	//private static Scanner output; 
	public static Terrain[][] readMap2D(String fileName, String tag)
	{
		File source = new File(fileName + ".map");
		String line = "";
		String[] elements;
		String[][] subElements = null;
		Terrain[][] retVal = new Terrain[0][0];
		System.out.println("TEST1");
		if(source.exists())
		{
			System.out.println("TEST2");
			try
			{
				System.out.println("TEST3");
				input = new Scanner(source);
				while(!line.contains(tag))
				{
					line = input.next();
					System.out.println(line);
				}
				retVal = new Terrain[input.nextInt()][];
				line = input.nextLine();
				subElements = new String[line.split(":").length][3];
				
				for(int y = 0; y < retVal.length; y++)
				{
					line = input.nextLine();
					elements = input.nextLine().split(":");
					subElements = new String[elements.length][3];
					for(int i = 0; i < elements.length; i++)
					{
						subElements[i] = elements[i].trim().split(",");
					}
					for(int x = 0; x < elements.length; x++)
					{
						
						retVal[x][y] = Terrain.readTerrain(
								Integer.parseInt(subElements[x][0]), 
								Integer.parseInt(subElements[x][1]),
								subElements[x][2].toLowerCase());
					}
				}
				
				input.close();
				
			}
			catch (FileNotFoundException e)
			{
				System.out.println("ERROR: File cannot be read.");
			}
		}
		return retVal;
	}
	
	
	public static void printMap() throws FileNotFoundException
	{
		int[][][] a = readMap3D("file.map", "@map");
		for(int n = 0; n < a.length; n++)
		{
			for(int i = 0; i < a.length; i++)
			{
				for(int q = 0; q < a[n][i].length; q++)
				{
					System.out.print(a[n][i][q] + ",");
				}
				System.out.print(":");
			}
			System.out.println();
		}
		System.out.println("DONE");
	}

	public static int[][][] readMap3D(String fileName, String tag) throws FileNotFoundException
	{
		int[][][] retVal = new int[0][0][0];;
		
		String line;
		String[] elements;
		String[] subElements;
		
		File sourceFile = new File(fileName);
		if(sourceFile.exists())
		{
			input = new Scanner(sourceFile);

			while(input.hasNextLine())
			{
				line = input.nextLine();
				if(line.matches(tag))
				{
					line = input.nextLine();
					elements = line.split(":");
					subElements = elements[0].split(",");
					
					retVal = new int[elements.length][elements.length][subElements.length];
					
					for(int i = 0; i < elements.length; i++)
					{
						for(int q = 0; q < subElements.length; q++)
						{
							retVal[0][i][q] = Integer.parseInt(subElements[q]);
						}
					}
					
					for(int n = 1; n < elements.length; n++)
					{
						line = input.nextLine();
						elements = line.split(":");
						
						for(int i = 0; i < elements.length; i++)
						{
							subElements = elements[i].split(",");
							for(int q = 0; q < subElements.length; q++)
							{
								retVal[n][i][q] = Integer.parseInt(subElements[q]);
							}
						}
					}
					
					break;
				}
			}
		}	
		return retVal;
	}
}
