import java.lang.Math;


public class Robot {
    double accelRate;
    double angAccelRate;
    double velocity;
    double angVel;
    double velCap;
    double angVelCap;
    double angle;
    double[] pos;
    Strategy strat;

    double[] goalPos;
    Double goalAngle;
    Action currentAction;
    double timeToPickUp;
    double timeToPlaceGround;
    double timeToPlaceLower;
    double timeToPlaceMiddle;
    double timeToPlaceHigh;
    public double getAccelRate() {
        return accelRate;
    }
    public double getAngAccelRate() {
        return angAccelRate;
    }
    public double getVelocity() {
        return velocity;
    }
    public double getAngVel() {
        return angVel;
    }
    public double getVelCap() {
        return velCap;
    }
    public double getAngVelCap() {
        return angVelCap;
    }
    public double getAngle() {
        return angle;
    }
    public double[] getPos() {
        return pos;
    }
    public double getTimeToPickUp() {
        return timeToPickUp;
    }
    public double getTimeToPlaceGround() {
        return timeToPlaceGround;
    }
    public double getTimeToPlaceLower() {
        return timeToPlaceLower;
    }
    public double getTimeToPlaceMiddle() {
        return timeToPlaceMiddle;
    }
    public double getTimeToPlaceHigh() {
        return timeToPlaceHigh;
    }
    public void setGoalPos(double[] goalPos) {
        this.goalPos = goalPos;
    }
    public void setGoalAngle(Double goalAngle) {
        this.goalAngle = goalAngle;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public Robot(double a, double vc, double[] position, double aa, double avc, double ang, Strategy s) {
        accelRate = a;
        angAccelRate = aa;
        velCap = vc;
        angVelCap = avc;
        pos = position;
        angle = ang;
        strat = s;

        velocity = velCap;
        angVel = angVelCap;
        currentAction = null;
        goalPos = null;
        goalAngle = null;
    }
    public void move() {
        // For now we don't actually care about acceleration
        // this assumes that we take a second
        // First, change the angle
        if (angle < goalAngle) {
            if (goalAngle - angle < angVelCap) {
                angle = goalAngle;
            }
            else {
                angle += angVelCap;
            }
        }
        else {
            if (angle - goalAngle < angVelCap) {
                angle = goalAngle;
            }
            else {
                angle -= angVelCap;
            }
        }
        // Next, determine angle we need to travel at
        double dy = goalPos[1] - pos[1];
        double dx = goalPos[0] - pos[0];
        double ang = Math.atan2(dy, dx);
        double distanceTo = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        if (distanceTo < velocity) {
            // just go there, don't worry about deacceleration.
            pos = goalPos;
            return;
        }
        double[] changes = {Math.cos(ang) * velocity, Math.sin(ang) * velocity};
        pos[0] += changes[0];
        pos[1] += changes[1];

    }
    public Action decideAction() {
        return strat.run(this);
    }

}
