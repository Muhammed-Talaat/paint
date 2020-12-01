package eg.edu.alexu.csd.oop.draw.cs;

import org.springframework.stereotype.Service;

@Service
public class FakeShape extends Shape{

	@Override
	public boolean checkIfShapeIntersectAPoint(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

}
