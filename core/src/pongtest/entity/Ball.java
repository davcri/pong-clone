package pongtest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Ball extends Sprite
{
	private int width = 50;
	private int height = 50;
	private Vector2 velocity;
	//private rotationSpeed = 0;
	
	public Ball(Vector2 position, Vector2 velocity)
	{	
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(1,0,0,1);
		pixmap.fillCircle(25, 25, 24);
		this.set(new Sprite(new Texture(pixmap)));
		
		setPosition(position.x, position.y);
		this.velocity = velocity;
	}
	
	public void update(float delta, Paddle paddleL, Paddle paddleR)
	{
		if (collisionTop() || collisionBottom())
		{
			// invert the direction on the y axis
			velocity.y = -velocity.y;
		}
		
		if (collisionPaddle(paddleL) || collisionPaddle(paddleR))
		{
			velocity.x = -velocity.x;
		}
			
		setPosition(getX() + velocity.x*delta, getY() + velocity.y*delta);
	}
	
	private boolean collisionPaddle(Paddle paddle) {
		boolean collision = false;
		
		return paddle.getBoundingRectangle().overlaps(this.getBoundingRectangle());
	}

	private boolean collisionBottom()
	{
		boolean collision = false;
	
		if (getY()<= 0)
			collision = true;
	
		return collision;
	}
	
	private boolean collisionTop()
	{
		boolean collision = false;
		float screenHeight = Gdx.graphics.getHeight();
		
		if (getY() + height >= screenHeight)
			collision = true;
		
		return collision;
	}
}
