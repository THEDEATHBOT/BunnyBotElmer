package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.Robot;
import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTo extends Command implements PIDOutput {

	private double pidRate;
	public PIDController controller;
	public double SetPoint;
	private double initAngle;
	
    public DriveTo(double distance) {
    	this(distance, Robot.driveTrain.rightEncoder);
    	}
    
    public DriveTo(double distance, PIDSource source) {
    	
    	requires(Robot.driveTrain);
    	SetPoint = distance;
    	
    	controller = new PIDController(RobotMap.Values.driveDistanceP, RobotMap.Values.driveDistanceI, RobotMap.Values.driveDistanceD, source, this);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	controller.setSetpoint(SetPoint + Robot.driveTrain.rightEncoder.getDistance());
    	controller.enable();
    	controller.setAbsoluteTolerance(3);
    	initAngle = Robot.driveTrain.ahrs.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	//Robot.driveTrain.SetVoltages(-pidRate, -0.06*Robot.driveTrain.ahrs.getAngle());
    	double angleOffset = (Robot.driveTrain.ahrs.getAngle() - initAngle);
    
    	if(angleOffset > 180) {
    		angleOffset -= 360;
    	} else if(angleOffset < -180) {
    		angleOffset += 360;
    	}
    	
    	double mult = RobotMap.Values.driveMult; //-.05
    	//Robot.driveTrain.SetVoltages(-Robot.clamp(1, -1, (pidRate + angleOffset * mult)), -Robot.clamp(1, -1, (pidRate - angleOffset * mult)));
    	
    	/*Current state of affairs:
    	 * Robot turns 90 degrees to the right
    	 */
    }
    	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return controller.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	controller.disable();
    	Robot.driveTrain.SetVoltages(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    public void pidWrite(double output) {
    	pidRate = output;
    	Robot.driveTrain.SetVoltages(-output, -output);
    	SmartDashboard.putBoolean("PID enabled", controller.isEnabled());
    	SmartDashboard.putBoolean("PID on target", controller.onTarget());
    	SmartDashboard.putNumber("Setpoint", SetPoint);
	}
}

