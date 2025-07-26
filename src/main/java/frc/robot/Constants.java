package frc.robot;

public class Constants 
{
    public class EndEffectorBase
    {
        public static final double CORAL_WIDTH = 30.0; // inches
        public static final int CORAL_COUNT = 4;
        public static final double CORAL_LENGTH = CORAL_WIDTH * CORAL_COUNT; // inches
        public static final double PIXELS_TO_INCHES = CORAL_WIDTH / 231.62; // inch/pixel
        public static final double M = -150.0 / 388.0; // slope

        public static final double S = 0; // pixels
        public static final double M_S = Math.sqrt(Math.pow(1719 - 1280, 2) + Math.pow(919-1089, 2)); // pixels
        public static final double C_1__ = Math.sqrt(Math.pow(1719 - 627, 2) + Math.pow(919 - 1341, 2)); // pixels
        public static final double C_2__ = Math.sqrt(Math.pow(1719 - 100, 2) + Math.pow(919 - 1545, 2)); // pixels

        public static final double INTAKE_SPEED = 800.0; //pixels / s

        public static final double X_OFFSET = 0;
        public static final double Y_OFFSET = 0;
    }
}
