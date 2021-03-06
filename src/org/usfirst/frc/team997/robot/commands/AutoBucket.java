package org.usfirst.frc.team997.robot.commands;

import org.usfirst.frc.team997.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoBucket extends CommandGroup {

    public AutoBucket() {
    	addSequential(new AutoShift(0));
    	addParallel(new Timercommand(2));
    	addSequential(new ZeroArmJoint());
    	addSequential(new ArmToAngle(RobotMap.Values.armPositionVertical));
    	//addSequential(new WaitCommand(0.5));
    	addSequential(new OpenClawCommand());
    	addSequential(new ArmToAngle(RobotMap.Values.armPositionForwardLevel));
    	//addSequential(new WaitCommand(1));
    	addSequential(new AutoCloseClawCommand());
    	//addSequential(new WaitCommand(1));
    	addSequential(new ArmToAngle(RobotMap.Values.armPositionMidForward));
    	
    }
}
