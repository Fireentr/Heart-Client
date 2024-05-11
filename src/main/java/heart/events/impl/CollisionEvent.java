package heart.events.impl;

import net.minecraft.util.AxisAlignedBB;

public class CollisionEvent {
    public double collisionX, collisionY, collisionZ;
    public boolean override;

    public double getCollisionX() {
        return collisionX;
    }

    public void setCollisionX(double collisionX) {
        this.collisionX = collisionX;
    }

    public double getCollisionY() {
        return collisionY;
    }

    public void setCollisionY(double collisionY) {
        this.collisionY = collisionY;
    }

    public double getCollisionZ() {
        return collisionZ;
    }

    public void setCollisionZ(double collisionZ) {
        this.collisionZ = collisionZ;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public CollisionEvent(double collisionX, double collisionY, double collisionZ, boolean override){
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.collisionZ = collisionZ;
        this.override = override;
    }

    public AxisAlignedBB box(){
        return AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(collisionX, collisionY, collisionZ);
    }
}
