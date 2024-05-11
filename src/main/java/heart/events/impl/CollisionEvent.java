package heart.events.impl;

import heart.events.Cancellable;
import net.minecraft.util.AxisAlignedBB;

public class CollisionEvent extends Cancellable {
    public double collisionX, collisionY, collisionZ;
    public boolean override;

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
