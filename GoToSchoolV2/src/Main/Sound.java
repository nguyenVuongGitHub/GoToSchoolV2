package src.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    List<Clip> clipList = new ArrayList<>(); // Danh sách để lưu trữ tất cả các Clip


    public Sound() {
        soundURL[0] = getClass().getResource("/sound/background.wav");
        soundURL[1] = getClass().getResource("/sound/Dungeon.wav");
        soundURL[2] = getClass().getResource("/sound/Loppy.wav");
        soundURL[3] = getClass().getResource("/sound/Map_Select.wav");
        soundURL[4] = getClass().getResource("/sound/choose.wav");
        soundURL[5] = getClass().getResource("/sound/pickupCoin.wav");
        soundURL[6] = getClass().getResource("/sound/Map_3.wav");
        soundURL[7] = getClass().getResource("/sound/Map_4.wav");
        soundURL[8] = getClass().getResource("/sound/fire_ball.wav");
        soundURL[9] = getClass().getResource("/sound/moon_light.wav");
        soundURL[10] = getClass().getResource("/sound/fire.wav");
        soundURL[11] = getClass().getResource("/sound/survival.wav");
        soundURL[12] = getClass().getResource("/sound/arrow.wav");
        soundURL[13] = getClass().getResource("/sound/arrows.wav");
        soundURL[14] = getClass().getResource("/sound/dame.wav");
        soundURL[15] = getClass().getResource("/sound/gojo.wav");
        soundURL[16] = getClass().getResource("/sound/bossshot.wav");
        soundURL[17] = getClass().getResource("/sound/click.wav");
        soundURL[18] = getClass().getResource("/sound/Teleport.wav");
        soundURL[19] = getClass().getResource("/sound/powerUp.wav");
        soundURL[20] = getClass().getResource("/sound/Heal.wav");

    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clipList.add(clip);
        }catch (Exception e) {
        }
    }

    public void play(){
        clip.start();
    }

    public void playSE_one() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close(); // Đóng clip khi phát xong
                }
            });
        }
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
    }
    public void stopAll() {
        for (Clip c : clipList) {
            if (c != null && c.isRunning()) {
                c.stop();
            }
        }
    }
    public void close() {
        for (Clip c : clipList) {
            if (c != null) {
                c.close();
            }
        }
        clipList.clear(); // Xóa danh sách sau khi đóng tất cả các clip
    }
}
