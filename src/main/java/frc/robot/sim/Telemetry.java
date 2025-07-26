package frc.robot.sim;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.EndEffector;

public class Telemetry 
{
    private static NetworkTableInstance instance = NetworkTableInstance.getDefault();
    private static NetworkTable table = instance.getTable("Telemetry");

    private static StructArrayPublisher<Pose2d> poses = table.getStructArrayTopic("poses", Pose2d.struct).publish();
    // private static StructPublisher<Pose2d> lfcoral = table.getStructTopic("Left Front Position of Coral", Pose2d.struct).publish();
    // private static DoublePublisher lf = table.getDoubleTopic("Pixels from S").publish();
    // private static DoublePublisher S = table.getDoubleTopic("S").publish();
    // private static DoublePublisher M_S = table.getDoubleTopic("M_S").publish();
    // private static DoublePublisher C_1__ = table.getDoubleTopic("C_1__").publish();
    // private static DoublePublisher C_2__ = table.getDoubleTopic("C_2__").publish();
    // private static DoublePublisher TH = table.getDoubleTopic("TH").publish();
    
    private static NetworkTable sensorsTable = table.getSubTable("Sensors");
    private static BooleanPublisher frontSensor = sensorsTable.getBooleanTopic("Front Sensor").publish();
    private static BooleanPublisher backSensor = sensorsTable.getBooleanTopic("Back Sensor").publish();

    private static IntegerPublisher coralScored = table.getIntegerTopic("Coral Scored").publish();
    private static int _coralScored = 0;


    static void incrementCoralScored ()
    {
        _coralScored++;
        coralScored.set(_coralScored);
    }

    public static void initialize ()
    {
    }

    public static void update ()
    {
        poses.set(EndEffector.getInstance().getRenderPoses());
        // lfcoral.set(EndEffector.getInstance().currentCoralPosition());
        // lf.set(EndEffector.getInstance().getLeftFrontCoralLocation());
        // S.set(Constants.EndEffectorBase.S);
        // M_S.set(Constants.EndEffectorBase.M_S);
        // C_1__.set(Constants.EndEffectorBase.C_1__);
        // C_2__.set(Constants.EndEffectorBase.C_2__);
        // TH.set(Math.atan(Constants.EndEffectorBase.M));
        frontSensor.set(EndEffector.getInstance().frontSensorHit());
        backSensor.set(EndEffector.getInstance().backSensorHit());

    }
}
