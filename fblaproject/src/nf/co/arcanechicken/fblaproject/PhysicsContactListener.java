package nf.co.arcanechicken.fblaproject;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PhysicsContactListener implements ContactListener {
	private Ship ship;

	public PhysicsContactListener(Ship ship) {
		this.ship = ship;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();
		System.out.println(a + " " + b);
		
		//don't try this at home either kids

		for (int i = 0; i < ship.getBullets().size; i++) {
			if (ship.getBullets().get(i).equals(a.getUserData())
					|| ship.getBullets().get(i).equals(b.getUserData())) {
				if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
					if (a.getUserData() instanceof Enemy) {
						((Enemy) (a.getUserData())).damage(((Bullet) b.getUserData()).getDamage());
						((Bullet) b.getUserData()).setDead(true);

					}
					if (b.getUserData() instanceof Enemy) {
						((Enemy) (b.getUserData())).damage(((Bullet) a.getUserData()).getDamage());
						((Bullet) a.getUserData()).setDead(true);
					}
				}
			}
		}
		if((a.getUserData() instanceof Bullet && ((Bullet)a.getUserData()).getBody().getLinearVelocity().y < 0) || (b.getUserData() instanceof Bullet && ((Bullet)b.getUserData()).getBody().getLinearVelocity().y < 0)){
			if (a.getUserData() instanceof Bullet || b.getUserData() instanceof Bullet) {
				if (a.getUserData() instanceof Ship) {
					((Ship) (a.getUserData())).damage(((Bullet) b.getUserData()).getDamage());
					((Bullet) b.getUserData()).setDead(true);

				}
				if (b.getUserData() instanceof Ship) {
					((Ship) (b.getUserData())).damage(((Bullet) a.getUserData()).getDamage());
					((Bullet) a.getUserData()).setDead(true);
				}
			}
		}

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
