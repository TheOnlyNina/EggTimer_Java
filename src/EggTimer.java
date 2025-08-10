//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.String;



public class EggTimer {
    static int secondsLeft;
    static Timer timer;

    public static void main(String[] args) {
        //creating a new window

        JFrame frame = new JFrame("Cook your eggs!");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(225, 248, 220)); // light beige

        //label title
        JLabel title = new JLabel("🍳 How do you want your eggs today?", SwingConstants.CENTER);
        title.setBounds(0, 20, 600, 30);
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        frame.add(title);

        //buttons

        //load images
        ImageIcon softIcon = new ImageIcon("soft_egg.png");
        ImageIcon hardIcon = new ImageIcon("hard_egg.png");

        //creates image buttons
        JButton softEgg = new JButton(softIcon);
        JButton hardEgg = new JButton(hardIcon);
        //remove borders
        softEgg.setBorderPainted(false);
        softEgg.setContentAreaFilled(false);
        hardEgg.setBorderPainted(false);
        hardEgg.setContentAreaFilled(false);
        //positions
        softEgg.setBounds(130, 60, 100, 100);
        hardEgg.setBounds(370, 60, 100, 100);
        //add to frame
        frame.add(softEgg);
        frame.add(hardEgg);

        //label message
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(0, 210, 600, 30);
        messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        frame.add(messageLabel);

        // Timer label
        JLabel timerLabel = new JLabel("", SwingConstants.CENTER); // empty text for now
        timerLabel.setBounds(0, 180, 600, 30);                      // position + size
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));      // font style
        frame.add(timerLabel);                                     // add to frame

        // buttons actions
        ActionListener eggClickAction = e -> {
            JButton clicked = (JButton) e.getSource();
            secondsLeft = clicked == softEgg ? 180 : 360; // soft = 3 min, hard = 6 min

            // hiding clicked egg
            clicked.setVisible(false);
            softEgg.setEnabled(false);
            hardEgg.setEnabled(false);

            // start timer
            timer = new Timer(1000, null);
            timer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (secondsLeft > 0) {
                        int min = secondsLeft / 60;
                        int sec = secondsLeft % 60;
                        String timeText = String.format("Time left: %02d:%02d", min, sec);
                        timerLabel.setText(timeText);

                        secondsLeft--;
                    } else {
                        timer.stop();
                        timerLabel.setText("");
                        messageLabel.setText("I'm done cooking! Take me out before I turn into a rock!");
                        playSound("alarm.wav"); // alarm sound
                    }
                }
            });
            timer.start();
        };
        softEgg.addActionListener(eggClickAction);
        hardEgg.addActionListener(eggClickAction);

        frame.setVisible(true);
    }

    public static void playSound(String fileName) {
        try {
            File soundFile = new File(fileName);
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }
}

