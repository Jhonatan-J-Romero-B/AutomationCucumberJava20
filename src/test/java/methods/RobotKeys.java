package methods;

import java.awt.*;

public class RobotKeys {
    Robot robot = new Robot();

    public RobotKeys() throws AWTException {
    }

    public void keyPressOne(int key){
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    public void keyPress(int key, int cantPress){
        for(int i = 0; i < cantPress; i++){
            robot.keyPress(key);
            robot.keyRelease(key);
        }
    }
}