public class AirHockey{
        public static void main(String[] arguments){
            
            final double malletSpeed = 2;

            GameArena game = new GameArena(1000,500);
            double colisionXVelocity;
            double colisionYVelocity;
            double magnitude1;

            drawTable(game);


            Ball redMallet = new Ball(250, 250, 100, "RED");
            Ball blueMallet = new Ball(750, 250, 100, "BLUE");
            Ball puck = new Ball(500, 250, 50, "WHITE");


            
            game.addBall(redMallet);
            game.addBall(blueMallet);
            game.addBall(puck);

            blueMallet.setFriction(10);
            redMallet.setFriction(10);

            while (true){


                game.pause();

                //moving the blue mallet to its side
                if(game.leftPressed()) blueMallet.accelerateX(-malletSpeed);
                if(game.rightPressed()) blueMallet.accelerateX(malletSpeed);
                if(game.upPressed()) blueMallet.accelerateY(-malletSpeed);
                if(game.downPressed()) blueMallet.accelerateY(malletSpeed);

                //confines the blue mallet to its side
                if(blueMallet.getXPosition() + blueMallet.getXVelocity() > 900) {blueMallet.setXPosition((900)); blueMallet.setXVelocity(0);}
                if(blueMallet.getXPosition() + blueMallet.getXVelocity() < 550) {blueMallet.setXPosition((550)); blueMallet.setXVelocity(0);}

                if(blueMallet.getYPosition() + blueMallet.getYVelocity() > 400) {blueMallet.setYPosition((400)); blueMallet.setYVelocity(0);}
                if(blueMallet.getYPosition() + blueMallet.getYVelocity() < 100) {blueMallet.setYPosition((100)); blueMallet.setYVelocity(0);}

                //moving the red mallet to its side
                if(game.letterPressed('a')) redMallet.accelerateX(-malletSpeed);
                if(game.letterPressed('d')) redMallet.accelerateX(malletSpeed);
                if(game.letterPressed('w')) redMallet.accelerateY(-malletSpeed);
                if(game.letterPressed('s')) redMallet.accelerateY(malletSpeed);

                //confines the red mallet to its side
                if(redMallet.getXPosition() + redMallet.getXVelocity() > 450) {redMallet.setXPosition((450)); redMallet.setXVelocity(0);}
                if(redMallet.getXPosition() + redMallet.getXVelocity() < 100) {redMallet.setXPosition((100)); redMallet.setXVelocity(0);}

                if(redMallet.getYPosition() + redMallet.getYVelocity() > 400) {redMallet.setYPosition((400)); redMallet.setYVelocity(0);}
                if(redMallet.getYPosition() + redMallet.getYVelocity() < 100) {redMallet.setYPosition((100)); redMallet.setYVelocity(0);}



                //Mallet colisions
                if(puck.collides(blueMallet)) puck.deflectOff(blueMallet);
                if(puck.collides(redMallet)) puck.deflectOff(redMallet);



                blueMallet.tick();
                redMallet.tick();
                puck.tick();
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
            game.addRectangle(new Rectangle(0,0,40,500,"BLACK"));


            game.addBall(new Ball(960,250, 204, "WHITE"));
            game.addBall(new Ball(960,250, 200, "BLACK"));
            game.addRectangle(new Rectangle(960,0,40,500,"BLACK"));
        }
    
}
