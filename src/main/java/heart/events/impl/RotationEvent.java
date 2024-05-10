package heart.events.impl;

public class RotationEvent {
    float yaw, pitch;

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw(){
        return yaw;
    }

    public float getPitch(){
        return pitch;
    }

    public RotationEvent(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }
}