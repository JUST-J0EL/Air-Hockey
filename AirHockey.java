import java.util.spi.ToolProvider;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;

public class AirHockey{
        public static void main(String[] arguments){
            
            Random rand = new Random(); 

            try
            {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File("hit.wav")));
                clip.start();
                while (!clip.isRunning())
                    Thread.sleep(10);
                while (clip.isRunning())
                    Thread.sleep(10);
                clip.close();
            }
            catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }


            
            final double malletSpeed = 1;

            GameArena game = new GameArena(1000,500);
            double colisionXVelocity;
            double colisionYVelocity;
            double magnitude1;

            drawTable(game);

            Text redScore = new Text("0", 200, 200, 320, "DARKGREY");
            Text blueScore = new Text("0", 200, 660, 320, "DARKGREY");

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
            puck.setFriction(1);

            while (true){



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
                if(puck.collides(blueMallet)) puck.deflectOff(blueMallet);
                if(puck.collides(redMallet)) puck.deflectOff(redMallet);
                //Goal post colisions
                if(puck.collides(topBlue)) puck.deflectOff(topBlue);
                if(puck.collides(bottomBlue)) puck.deflectOff(bottomBlue);
                if(puck.collides(topRed)) puck.deflectOff(topRed);
                if(puck.collides(bottomRed)) puck.deflectOff(bottomRed);

                //confines the blue mallet to its side
                if(blueMallet.getXPosition() + blueMallet.getXVelocity() > 900) {blueMallet.setXPosition((900)); blueMallet.setXVelocity(0);}
                if(blueMallet.getXPosition() + blueMallet.getXVelocity() < 550) {blueMallet.setXPosition((550)); blueMallet.setXVelocity(0);}

                if(blueMallet.getYPosition() + blueMallet.getYVelocity() > 400) {blueMallet.setYPosition((400)); blueMallet.setYVelocity(0);}
                if(blueMallet.getYPosition() + blueMallet.getYVelocity() < 100) {blueMallet.setYPosition((100)); blueMallet.setYVelocity(0);}



                //confines the red mallet to its side
                if(redMallet.getXPosition() + redMallet.getXVelocity() > 450) {redMallet.setXPosition((450)); redMallet.setXVelocity(0);}
                if(redMallet.getXPosition() + redMallet.getXVelocity() < 100) {redMallet.setXPosition((100)); redMallet.setXVelocity(0);}

                if(redMallet.getYPosition() + redMallet.getYVelocity() > 400) {redMallet.setYPosition((400)); redMallet.setYVelocity(0);}
                if(redMallet.getYPosition() + redMallet.getYVelocity() < 100) {redMallet.setYPosition((100)); redMallet.setYVelocity(0);}





                blueMallet.tick();
                redMallet.tick();
                puck.tick();
                game.pause();

            }



            
        }


        

        /**
         * Draws the table and lines on th interface
         * @param game the game Arena where it will be drawn
         */
        private static void drawTable(GameArena game){
            //Border
            game.addRectangle(new Rectangle(40,40,460,420,"RED"));
            game.addRectangle(new Rectangle(500,40,460,420,"BLUE"));
            game.addRectangle(new Rectangle(50,50,900,400,"BLACK"));

            //Middel            
            game.addBall(new Ball(500,250, 204, "WHITE"));
            game.addBall(new Ball(500,250, 200, "BLACK"));
            game.addLine(new Line(500,40, 500, 460, 2, "WHITE"));

            //goals
            game.addBall(new Ball(50,250, 204, "WHITE"));
            game.addBall(new Ball(50,250, 200, "BLACK"));
            game.addRectangle(new Rectangle(0,148,45,204,"BLACK"));


            game.addBall(new Ball(960,250, 204, "WHITE"));
            game.addBall(new Ball(960,250, 200, "BLACK"));
            game.addRectangle(new Rectangle(955,148,45,204,"BLACK"));




        }
    
}
