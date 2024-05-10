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
    private long coinNeedUpgrade = 0;

    private short numberLeversUnlocked;
    private short maxNumberSkillsSupportUnlocked;
    private short maxNumberSkillsAttackUnlocked;
    public UserManager() {
    }
    private void readFileArrowLight() {
        int lever = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infArrowLight.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);

            br.readLine(); // break line title

            int index = 0;
            for(int i = 0; i < BaseArrowLight.MAX_LEVER; i++) {
                if((line = br.readLine()) != null) {
                    String[] token = line.split(";");
                    BaseArrowLight.timeReduce[index]  = Integer.parseInt(token[0]);
                    BaseArrowLight.damage[index]  = Integer.parseInt(token[1]);
                    BaseArrowLight.speed[index]  = Integer.parseInt(token[2]);
                    BaseArrowLight.distance[index]  = Integer.parseInt(token[3]);
                    index++;
                }
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        BaseArrowLight.LEVER = lever;
    }
    private void readFileMultiArrowLight() {
        int lever = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infMultiArrow.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            br.readLine(); // break line title

            int index = 0;
            while((line = br.readLine()) != null) {
                String[] token = line.split(";");
                BaseMultiArrow.timeReduce[index] = Integer.parseInt(token[0]);
                BaseMultiArrow.damage[index] = Integer.parseInt(token[1]);
                BaseMultiArrow.speed[index] = Integer.parseInt(token[2]);
                BaseMultiArrow.distance[index] = Integer.parseInt(token[3]);
                index++;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        BaseMultiArrow.LEVER = lever;
    }
    private void readFileCircleFire() {
        int lever = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infCircleFire.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            br.readLine(); // break line title
            int index = 0;
            while((line = br.readLine()) != null) {
                String[] token = line.split(";");
                BaseCircleFire.timeReduce[index] = Integer.parseInt(token[0]);
                BaseCircleFire.numberBurning[index] = Integer.parseInt(token[1]);
                BaseCircleFire.damage[index] = Integer.parseInt(token[2]);
                BaseCircleFire.radius[index] = Integer.parseInt(token[3]);
                index++;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        BaseCircleFire.LEVER = lever;
    }
    private void readFileMoonLight() {
        int lever = 0;
        try {
            InputStream input = getClass().getResourceAsStream("/user/infMoonLight.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine(); // read lever
            lever = Integer.parseInt(line.split(":")[1]);
            br.readLine(); // break line title
            int index = 0;
            while((line = br.readLine()) != null) {
                String[] token = line.split(";");
                BaseMoonLight.timeReduce[index] = Integer.parseInt(token[0]);
                BaseMoonLight.damage[index] = Integer.parseInt(token[1]);
                BaseMoonLight.speed[index] = Integer.parseInt(token[2]);
                index++;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        BaseMoonLight.LEVER = lever;
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
                    line = "lever :" + BaseArrowLight.LEVER;
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
    private void saveFileMultiArrowLight() {
        try{
            String filePath = "GotoSchoolV2/res/user/infMultiArrow.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // Kiểm tra nếu dòng chứa "lever"
                if (line.startsWith("lever")) {
                    // Cập nhật giá trị "lever" từ 2 thành 3
                    line = "lever :" + BaseMultiArrow.LEVER;
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
    private void saveFileMoonLight() {
        try{
            String filePath = "GotoSchoolV2/res/user/infMoonLight.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // Kiểm tra nếu dòng chứa "lever"
                if (line.startsWith("lever")) {
                    // Cập nhật giá trị "lever" từ 2 thành 3
                    line = "lever :" + BaseMoonLight.LEVER;
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
    private void saveFileCircleFire() {
        try{
            String filePath = "GotoSchoolV2/res/user/infCircleFire.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // Kiểm tra nếu dòng chứa "lever"
                if (line.startsWith("lever")) {
                    // Cập nhật giá trị "lever" từ 2 thành 3
                    line = "lever :" + BaseMultiArrow.LEVER;
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
        saveFileMultiArrowLight();
        saveFileCircleFire();
        saveFileMoonLight();
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
    public long getCoinNeedUpgrade() {
        return coinNeedUpgrade;
    }

    public void setCoinNeedUpgrade(long coinNeedUpgrade) {
        this.coinNeedUpgrade = coinNeedUpgrade;
    }
}
