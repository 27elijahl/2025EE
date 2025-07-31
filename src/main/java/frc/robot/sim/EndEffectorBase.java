package frc.robot.sim;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.*;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Do not modify!
 */
public abstract class EndEffectorBase extends SubsystemBase
{
    private double leftFrontCoralLocation; // pixels from s

    private double lastTime = 0.0;

    private double currentSpeed = 0.0;

    private Pose2d[] renderPoses = new Pose2d[Constants.EndEffectorBase.CORAL_COUNT];

    private double frontLeftXCoordinate (double leftFrontCoralLocation)
    {
        return leftFrontCoralLocation / Math.sqrt(Constants.EndEffectorBase.M * Constants.EndEffectorBase.M + 1)
            + (2000 - 1719);
    }
    
    private double frontLeftYCoordinate (double leftFrontCoralLocation)
    {
        return Constants.EndEffectorBase.M * leftFrontCoralLocation 
            / Math.sqrt(Constants.EndEffectorBase.M * Constants.EndEffectorBase.M + 1)
            - 919;

    }

    private void intakeCoral ()
    {
        leftFrontCoralLocation = 0.0;
    }

    protected void setOutput (double speed)
    {
        currentSpeed = speed;
    }

    protected boolean frontSensorHit ()
    {
        return Constants.EndEffectorBase.C_2__ < leftFrontCoralLocation &&
            Constants.EndEffectorBase.C_2__ > leftFrontCoralLocation - 
            Constants.EndEffectorBase.CORAL_LENGTH / Constants.EndEffectorBase.PIXELS_TO_INCHES;
    }

    protected boolean backSensorHit ()
    {
        return Constants.EndEffectorBase.C_1__ < leftFrontCoralLocation &&
            Constants.EndEffectorBase.C_1__ > leftFrontCoralLocation -
            Constants.EndEffectorBase.CORAL_LENGTH / Constants.EndEffectorBase.PIXELS_TO_INCHES;
    }

    @Override
    public final void periodic ()
    {
        if (lastTime == 0.0) 
        {
            lastTime = System.currentTimeMillis();
            return;
        }
        double timeElapsed = System.currentTimeMillis() - lastTime;
        lastTime += timeElapsed;
   
        if (leftFrontCoralLocation < Constants.EndEffectorBase.M_S)
        {
            leftFrontCoralLocation += Constants.EndEffectorBase.INTAKE_SPEED * timeElapsed / 1000.0;
        }
        else
        {
            leftFrontCoralLocation += currentSpeed * timeElapsed / 1000.0;
        }

        // render with poses
        {
            double th = Math.atan(Constants.EndEffectorBase.M);
            double xoff = 15.0 / Constants.EndEffectorBase.PIXELS_TO_INCHES * Math.sqrt(2.0) * Math.cos(th + Math.PI / 4.0);
            double yoff = 15.0 / Constants.EndEffectorBase.PIXELS_TO_INCHES * Math.sqrt(2.0) * Math.sin(th + Math.PI / 4.0);

            for (int i = 0; i < Constants.EndEffectorBase.CORAL_COUNT; i++)
            {
                renderPoses[i] = new Pose2d(new Translation2d(
                    Meters.convertFrom((frontLeftXCoordinate(leftFrontCoralLocation - 30.0 / Constants.EndEffectorBase.PIXELS_TO_INCHES * (i + 1)) + xoff) 
                        * Constants.EndEffectorBase.PIXELS_TO_INCHES, Inches) + Constants.EndEffectorBase.X_OFFSET,
                    Meters.convertFrom(-(frontLeftYCoordinate(leftFrontCoralLocation - 30.0 / Constants.EndEffectorBase.PIXELS_TO_INCHES * (i + 1)) + yoff) 
                        * Constants.EndEffectorBase.PIXELS_TO_INCHES, Inches) + Constants.EndEffectorBase.Y_OFFSET),
                    new Rotation2d(-th));
            }
        }

        if (!frontSensorHit() && !backSensorHit() && leftFrontCoralLocation > Constants.EndEffectorBase.C_2__)
        {
            Telemetry.incrementCoralScored();
            intakeCoral();
        }
    }

    public Pose2d[] getRenderPoses ()
    {
        return renderPoses;
    }

    // public Pose2d currentCoralPosition ()
    // {
    //     return new Pose2d(new Translation2d(
    //         Meters.convertFrom(frontLeftXCoordinate(leftFrontCoralLocation) * Constants.EndEffectorBase.PIXELS_TO_INCHES, Inches)
    //             + Constants.EndEffectorBase.X_OFFSET,
    //         Meters.convertFrom(-frontLeftYCoordinate(leftFrontCoralLocation) * Constants.EndEffectorBase.PIXELS_TO_INCHES, Inches)
    //             + Constants.EndEffectorBase.Y_OFFSET
    //     ), new Rotation2d(0));
    // }

    // public double getLeftFrontCoralLocation ()
    // {
    //     return leftFrontCoralLocation;
    // }

}
