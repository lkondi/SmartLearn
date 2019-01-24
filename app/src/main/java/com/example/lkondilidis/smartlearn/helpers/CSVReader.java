package com.example.lkondilidis.smartlearn.helpers;

import com.example.lkondilidis.smartlearn.model.Lecture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    InputStream inputStream;

    public CSVReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<Lecture> read(){
        List<Lecture> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                if(row[0].startsWith("0") || row[0].startsWith("1")) {
                //resultList.add(row);
                    if(row.length >= 2 && row[1].length() >= 2) {
                        Lecture lecture = new Lecture();
                        String lectureId = row[0];
                        String lectureName = row[1];
                        lectureId = checkString(lectureId);
                        lectureName = checkString(lectureName);
                        if(onlyHasNummerals(lectureId)){
                            lecture.setId(Integer.parseInt(lectureId));
                            lecture.setName(lectureName);
                            resultList.add(lecture);
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }

    private boolean onlyHasNummerals(String lectureId) {
        char[] chars = lectureId.toCharArray();
        for(char c: chars){
            if(!isNumeral(c)){
                return false;
            }
        }
        return true;
    }

    private boolean isNumeral(char c) {
        if(c == '0' ||c == '1' ||c == '2' ||c == '3' ||c == '4' ||c == '5' ||c == '6' ||c == '7' ||c == '8' ||c == '9'){
            return true;
        }
        return false;
    }

    private String checkString(String s) {
        String result = s;
        int length = s.length();
        if(s.endsWith("\"")){
            result = s.substring(0, length-2);

        }
        if(s.startsWith("\"")){
            result = s.substring(1);
        }
        return result;
    }
}
