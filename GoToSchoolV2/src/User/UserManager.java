/**
 * This class contains all user information ( skill unlock, coin, experience,....
 * */

package User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AttackSkill.*;
import Entity.*;
import baseAttribute.BaseArrowLight;
import baseAttribute.BaseCircleFire;
import baseAttribute.BaseMoonLight;
import baseAttribute.BaseMultiArrow;

public class UserManager {

    private long coin;
    private short numberLeversUnlocked;
    private short maxNumberSkillsSupportUnlocked;
    private short maxNumberSkillsAttackUnlocked;
    public UserManager() {
    }
    private void readFileArrowLight() {
        int lever = 0;
        int damage = 0;
        int timeReduce = 0;
        int speed = 0;
        int distance = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infArrowLight.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            for(int i = 1; i <= lever; i++) {
                br.readLine(); // break line
            }
            if((line = br.readLine()) != null) {
                String[] token = line.split(";");
                timeReduce = Integer.parseInt(token[0]);
                damage = Integer.parseInt(token[1]);
                speed = Integer.parseInt(token[2]);
                distance = Integer.parseInt(token[3]);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        ArrowLight.LEVER = lever;
        BaseArrowLight.damage = damage;
        BaseArrowLight.timeReduce = timeReduce;
        BaseArrowLight.speed = speed;
        BaseArrowLight.distance = distance;
    }
    private void readFileMultiArrowLight() {
        int lever = 0;
        int damage = 0;
        int timeReduce = 0;
        int speed = 0;
        int distance = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infMultiArrow.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            for(int i = 1; i <= lever; i++) {
                br.readLine(); // break line
            }
            if((line = br.readLine()) != null) {
                String[] token = line.split(";");
                damage = Integer.parseInt(token[0]);
                timeReduce = Integer.parseInt(token[1]);
                speed = Integer.parseInt(token[2]);
                distance = Integer.parseInt(token[3]);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        MultiArrow.LEVER = lever;
        BaseMultiArrow.damage = damage;
        BaseMultiArrow.timeReduce = timeReduce;
        BaseMultiArrow.speed = speed;
        BaseMultiArrow.distance = distance;
    }
    private void readFileCircleFire() {
        int lever = 0;
        int timeReduce = 0;
        int numberBurning = 0;
        int damage = 0;
        int radius = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infCircleFire.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            for(int i = 1; i <= lever; i++) {
                br.readLine(); // break line
            }
            if((line = br.readLine()) != null) {
                String[] token = line.split(";");
                timeReduce = Integer.parseInt(token[0]);
                numberBurning = Integer.parseInt(token[1]);
                damage = Integer.parseInt(token[2]);
                radius = Integer.parseInt(token[3]);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        CircleFire.LEVER = lever;
        BaseCircleFire.timeReduce = timeReduce;
        BaseCircleFire.numberBurning = numberBurning;
        BaseCircleFire.damage = damage;
        BaseCircleFire.radius = radius;
    }
    private void readFileMoonLight() {
        int lever = 0;
        int timeReduce = 0;
        int damage = 0;
        int speed = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infMoonLight.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            for(int i = 1; i <= lever; i++) {
                br.readLine(); // break line
            }
            if((line = br.readLine()) != null) {
                String[] token = line.split(";");
                timeReduce = Integer.parseInt(token[0]);
                damage = Integer.parseInt(token[1]);
                speed = Integer.parseInt(token[2]);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        MoonLight.LEVER = lever;
        BaseMoonLight.timeReduce = timeReduce;
        BaseMoonLight.speed = speed;
        BaseMoonLight.damage = damage;
    }
    public void readAttributeClasses() {
        readFileArrowLight();
        readFileMultiArrowLight();
        readFileCircleFire();
        readFileMoonLight();
    }
    public void readFile() {
        try {
            InputStream input = getClass().getResourceAsStream("/user/infUser.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            br.readLine(); // break first line
            while ((line = br.readLine()) != null) {
                String[] token = line.split(";");
                coin = Long.parseLong(token[0]);
                maxNumberSkillsSupportUnlocked = Short.parseShort(token[1]);
                maxNumberSkillsAttackUnlocked = Short.parseShort(token[2]);
                numberLeversUnlocked = Short.parseShort(token[3]);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveFileUser() {
        try{
            FileWriter fw = new FileWriter("GotoSchoolV2/res/user/infUser.txt");
            fw.write("coin; max number skill support unlocked; max number skill attack unlocked; number lever unlocked \n");
            fw.write(coin + ";" + maxNumberSkillsSupportUnlocked + ";" + maxNumberSkillsAttackUnlocked + ";" + numberLeversUnlocked + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
    private void saveFileArrowLight() {
        try{
            String filePath = "GotoSchoolV2/res/user/infArrowLight.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // Kiểm tra nếu dòng chứa "lever"
                if (line.startsWith("lever")) {
                    // Cập nhật giá trị "lever" từ 2 thành 3
                    line = "lever :" + ArrowLight.LEVER;
                    System.out.println(line);
                }
                content.append(line).append("\n");
            }
            reader.close();
            FileWriter fw = new FileWriter(filePath);
            fw.write(content.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
    }

    public void saveFile() {
        saveFileUser();
        saveFileArrowLight();
    }
    public long getCoin() {
        return coin;
    }

    public short getNumberLeversUnlocked() {
        return numberLeversUnlocked;
    }

    public short getMaxNumberSkillsSupportUnlocked() {
        return maxNumberSkillsSupportUnlocked;
    }
    public void setCoin(long coin) {
        this.coin = coin;
    }

    public void setNumberLeversUnlocked(short numberLeversUnlocked) {
        this.numberLeversUnlocked = numberLeversUnlocked;
    }
    public short getMaxNumberSkillsAttackUnlocked() {
        return maxNumberSkillsAttackUnlocked;
    }
    public short getMaxNumberLevers() {
        return (short) 5;
    }
}
