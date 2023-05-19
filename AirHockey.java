import java.util.spi.ToolProvider;
import java.io.*;
import javax.sound.sampled.*;

import java.io.File;


import java.util.Random;

public class AirHockey{
        public static void main(String[] arguments){
            
            Random rand = new Random(); 


            
            final double malletSpeed = 1;

            GameArena game = new GameArena(1000,500);
            double colisionXVelocity;
            double colisionYVelocity;
            double magnitude1;


            String goalsToWin = "5";

            //count of the number of game ticks
            int count = 0;
            //the count that the goal was scored on
            int goalOn = 0;//saves the tic that the goal was scored on
            boolean goal = false;//true when a goal is scored
            boolean gameOver = false;
            double newPuckX = 0;//saves the X position where the puck should go after a goal
            boolean pPrePressed = false; //used to detect when 'p' is presed 
            boolean mPrePressed = false; //used to detect when 'm' is presed 
            boolean vPrePressed = false; //used to detect when 'v' is presed 
            boolean sound = true;
            boolean centre = false; //true if centre is puck

            //Middle
            Ball whiteMiddle = new Ball(500,250, 204, "WHITE");
            Ball blackMiddle = new Ball(500,250, 200, "BLACK");

            drawTable(game, whiteMiddle, blackMiddle);


            Text redScore = new Text("0", 200, 200, 320, "DARKGREY");
            Text blueScore = new Text("0", 200, 660, 320, "DARKGREY");
            Text winner = new Text("", 140, 300, 170, "BLUE");
            Text wins = new Text("", 90, 350, 290, "WHITE");
            Text restart = new Text("", 30, 275, 400, "WHITE");//new Text(", 50, 300, 700, "WHITE");

            game.addText(winner);
            game.addText(wins);
            game.addText(restart);

            game.addText(redScore);
            game.addText(blueScore);

            //posts
            Ball topBlue = new Ball(955,148, 10, "BLUE");
            Ball bottomBlue = new Ball(955,352, 10, "BLUE");
            Ball topRed = new Ball(45,148, 10, "RED");
            Ball bottomRed = new Ball(45,352, 10, "RED");


            game.addBall(topBlue);
            game.addBall(bottomBlue);
            game.addBall(topRed);
            game.addBall(bottomRed);

            //mallets & puck
            Ball redMallet = new Ball(250, 250, 100, "RED");
            Ball blueMallet = new Ball(750, 250, 100, "BLUE");
            //random ofset up or down so the puck will always go of to one side
            Ball puck = new Ball(500, 248 + rand.nextInt(2)*4, 50, "WHITE");

            game.addBall(redMallet);
            game.addBall(blueMallet);
            game.addBall(puck);

            blueMallet.setFriction(10);
            redMallet.setFriction(10);
            blueMallet.setDissapation(80);
            redMallet.setDissapation(80);

            puck.setFriction(1);

            while (true){
                count += 1;


                //moving the blue mallet to its side
                if(game.leftPressed()) blueMallet.accelerateX(-malletSpeed);
                if(game.rightPressed()) blueMallet.accelerateX(malletSpeed);
                if(game.upPressed()) blueMallet.accelerateY(-malletSpeed);
                if(game.downPressed()) blueMallet.accelerateY(malletSpeed);
                
                //moving the red mallet to its side
                if(game.letterPressed('a')) redMallet.accelerateX(-malletSpeed);
                if(game.letterPressed('d')) redMallet.accelerateX(malletSpeed);
                if(game.letterPressed('w')) redMallet.accelerateY(-malletSpeed);
                if(game.letterPressed('s')) redMallet.accelerateY(malletSpeed);
                
                //Mallet colisions
                if(puck.collides(blueMallet)) puck.deflectOff(blueMallet, sound);
                if(puck.collides(redMallet)) puck.deflectOff(redMallet, sound);
                //Goal post colisions
                if(puck.collides(topBlue)) puck.deflectOff(topBlue, sound);
                if(puck.collides(bottomBlue)) puck.deflectOff(bottomBlue, sound);
                if(puck.collides(topRed)) puck.deflectOff(topRed, sound);
                if(puck.collides(bottomRed)) puck.deflectOff(bottomRed, sound);

                //confines the blue mallet to its side
                if(blueMallet.getXPosition() > 950 - blueMallet.getSize()/2) {blueMallet.setXPosition((950 - blueMallet.getSize()/2)); blueMallet.setXVelocity(0);}
                if(blueMallet.getXPosition() < 500 + blueMallet.getSize()/2) {blueMallet.setXPosition((500 + blueMallet.getSize()/2)); blueMallet.setXVelocity(0);}

                if(blueMallet.getYPosition()  > 450 - blueMallet.getSize()/2) {blueMallet.setYPosition((450 - blueMallet.getSize()/2)); blueMallet.setYVelocity(0);}
                if(blueMallet.getYPosition()  < 50 + blueMallet.getSize()/2) {blueMallet.setYPosition((50 + blueMallet.getSize()/2)); blueMallet.setYVelocity(0);}



                //confines the red mallet to its sideS
                if(redMallet.getXPosition() > 500 - redMallet.getSize()/2) {redMallet.setXPosition((500 - redMallet.getSize()/2)); redMallet.setXVelocity(0);}
                if(redMallet.getXPosition() < 50 + redMallet.getSize()/2) {redMallet.setXPosition((50 + redMallet.getSize()/2)); redMallet.setXVelocity(0);}

                if(redMallet.getYPosition() > 450 - redMallet.getSize()/2) {redMallet.setYPosition((450 - redMallet.getSize()/2)); redMallet.setYVelocity(0);}
                if(redMallet.getYPosition() < 50 + redMallet.getSize()/2) {redMallet.setYPosition((50 + redMallet.getSize()/2)); redMallet.setYVelocity(0);}


                //check for Goals
                if(!goal && puck.getXPosition() > 955){
                    if(sound)goalNoise();
                    redScore.setText(Integer.toString(Integer.parseInt(redScore.getText())+1));
                    newPuckX = 600;
                    goal = true;
                    goalOn = count;
                }
                if(!goal && puck.getXPosition() < 45){
                    if(sound)goalNoise();
                    blueScore.setText(Integer.toString(Integer.parseInt(blueScore.getText())+1));
                    newPuckX = 400;
                    goal = true;
                    goalOn = count;
                }
                
                //reset positoins after goalS
                if (goal && count == goalOn + 50 && !gameOver){
                    goal = false;
                    puck.setXPosition(newPuckX);
                    puck.setYPosition(248 + rand.nextInt(2)*4);
                    puck.setXVelocity(0);
                    puck.setYVelocity(0);
                    blueMallet.setXPosition(750);
                    blueMallet.setYPosition(250);
                    blueMallet.setXVelocity(0);
                    blueMallet.setYVelocity(0);
                    redMallet.setXPosition(250);
                    redMallet.setYPosition(250);
                    redMallet.setXVelocity(0);
                    redMallet.setYVelocity(0);
                    
                }
                //check if game won
                if(!gameOver && goal && ((blueScore.getText().equals(goalsToWin))|| redScore.getText().equals(goalsToWin))){
                    if(sound)winNoise();
                    blueScore.setColour("WHITE");
                    redScore.setColour("WHITE");
                    gameOver = true;

                    if(blueScore.getText().equals(goalsToWin)){
                        winner.setColour("BLUE");
                        winner.setText("BLUE");
                    }
                    if(redScore.getText().equals(goalsToWin)){
                        winner.setColour("RED");
                        winner.setText(" RED");
                    }
                    wins.setText("WINS!");;
                    restart.setText("PRESS [ENTER] TO RESTART");
                }

                //restart game
                if (gameOver && game.enterPressed()){
                    gameOver = false;
                    goal = false;
                    winner.setText("");
                    wins.setText("");
                    restart.setText("");
                    redScore.setText("0");
                    blueScore.setText("0");
                    redScore.setColour("DARKGREY");
                    blueScore.setColour("DARkGREY");
                    redMallet.setXPosition(250);
                    blueMallet.setXPosition(750);
                    redMallet.setYPosition(250);
                    blueMallet.setYPosition(250);
                    puck.setXPosition(500);
                    puck.setYPosition(248 + rand.nextInt(2)*4);
                    puck.setXVelocity(0);
                    puck.setYVelocity(0);
                    blueMallet.setXVelocity(0);
                    blueMallet.setYVelocity(0);
                    redMallet.setXVelocity(0);
                    redMallet.setYVelocity(0);
                }

                //cheat codes

                //swap with puck
                if(game.letterPressed('p') && !pPrePressed){
                   
                   
                    Ball temp;
                    pPrePressed = true;
                    if(puck.getXPosition() < 500 - puck.getSize()/2){
                        temp = redMallet;
                        redMallet = puck;
                        puck = temp;
                    }
                    if(puck.getXPosition() > 500 + puck.getSize()/2){
                        System.out.println("swap");
                        temp = blueMallet;
                        blueMallet = puck;
                        puck = temp;
                    }
                

                }

                if(!game.letterPressed('p'))pPrePressed = false;


                //give middle colisions
                if(game.letterPressed('m') && !mPrePressed){
                    mPrePressed =true;

                    if(centre)centre = false;
                    else centre = true;
                    Ball temp = whiteMiddle;
                    whiteMiddle = puck;
                    puck = temp;
                }
                if(!game.letterPressed('m'))mPrePressed = false;

                if (centre){
                    blackMiddle.setXPosition(puck.getXPosition() + puck.getXVelocity());
                    blackMiddle.setYPosition(puck.getYPosition() + puck.getYVelocity());

                }

                //give middle colisions
                if(game.letterPressed('v') && !vPrePressed){
                    vPrePressed =true;

                    if(sound)sound = false;
                    else sound = true;
                }
                if(!game.letterPressed('v'))vPrePressed = false;
                


                blueMallet.tick(sound);
                redMallet.tick(sound);
                puck.tick(sound);

                game.pause();

            }





            
        }


        

        /**
         * Draws the table and lines on th interface
         * @param game the game Arena where it will be drawn
         */
        private static void drawTable(GameArena game, Ball whiteMiddle, Ball blackMiddle){
            //Border
            game.addRectangle(new Rectangle(40,40,460,420,"RED"));
            game.addRectangle(new Rectangle(500,40,460,420,"BLUE"));
            game.addRectangle(new Rectangle(50,50,900,400,"BLACK"));



            game.addBall(whiteMiddle);
            game.addBall(blackMiddle);
            game.addLine(new Line(500,40, 500, 460, 2, "WHITE"));

            //goals
            game.addBall(new Ball(50,250, 204, "WHITE"));
            game.addBall(new Ball(50,250, 200, "BLACK"));
            game.addRectangle(new Rectangle(0,148,45,204,"BLACK"));


            game.addBall(new Ball(960,250, 204, "WHITE"));
            game.addBall(new Ball(960,250, 200, "BLACK"));
            game.addRectangle(new Rectangle(955,148,45,204,"BLACK"));




        }
        /**
         * plays the goal noise
         */
        private static void goalNoise(){


			
			try {
				File soundFile = new File("fanfare.wav");
				if (!soundFile.exists()) {
					System.err.println("Error: File not found: " + soundFile.getAbsolutePath());
					return;
				}
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            	gainControl.setValue(0);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
		    }

	    }

        /*
         * plays the win noise
         */
        private static void winNoise(){


			
			try {
				File soundFile = new File("applause.wav");
				if (!soundFile.exists()) {
					System.err.println("Error: File not found: " + soundFile.getAbsolutePath());
					return;
				}
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            	gainControl.setValue(0);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
		    }
        }

    
}
