package com.threeNews.springproject.service;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @author Chen Yinqi
 */
@RestController
public class ResultService {
    /**
     * @param commandStr command
     * @param folderPath absolute path of folder
     * @param fileName fileName
     * @param toSave the file path to save
     * @return result of execute.
     */

    public String exeCmd(String commandStr, String folderPath, String fileName, String toSave) {
        String result = "";
        try
        {
            System.out.println(commandStr);
            Process process = Runtime.getRuntime ().exec (commandStr);
            SequenceInputStream sis = new SequenceInputStream (process.getInputStream (), process.getErrorStream ());
            InputStreamReader isr = new InputStreamReader (sis, "gbk");
            BufferedReader br = new BufferedReader (isr);
            // Construct a write flow and absolute path
            String savePath = toSave;
            File filePath = new File(savePath);

            if (!filePath.exists()){
                filePath.mkdirs();
            }

            FileWriter fw = new FileWriter(filePath+"\\result.txt");
            // read
            String line = null;
            while (null != ( line = br.readLine () ))
            {
                // write
                fw.write(line + "\n");
                result += (line+"\n");

            }
            // Refresh flow
            fw.flush();
            // Close output flow
            fw.close();
            // Close Process
            process.getOutputStream().close();
            process.destroy ();
            br.close ();
            isr.close ();
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
        return result;
    }

    /**
     * @param file absolute path of file
     * @param lang language: Java, Python, C
     * @param toSave path to save
     * @param para parameter
     * @return result of execute.
     */
    public String compile(String file, String lang, String toSave, String para) {
        String result;
        String commandStr = "";
        // The absolute path of folder
        String folderPath = "";
        // The absolute path of complied class
        String fileClassPath = "";
        // The absolute path of running class
        String fileRunPath = "";
        String[] s = file.split("\\\\");
        //Pick up the fileName
        String fileName = s[s.length-1].split("\\.")[0];
        // The absolute path of folder
        for (int i = 0; i < s.length-1; i++){
            folderPath += (s[i]+"\\");
        }
        commandStr = "python "+file +" "+para;
        System.out.println("run: "+commandStr);
        result = this.exeCmd(commandStr, folderPath,fileName, toSave);
        return result;
    }
}