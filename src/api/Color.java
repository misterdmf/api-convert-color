package api;

public class Color {
	int r, g, b;
	String hex;
	float h, s, l;
	TypeColor typeOriginal;

	public Color(TypeColor t) {
		typeOriginal = t;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}

	public float getS() {
		return s;
	}

	public void setS(float s) {
		this.s = s;
	}

	public float getL() {
		return l;
	}

	public void setL(float l) {
		this.l = l;
	}

	public void convert() {
		if (typeOriginal == TypeColor.RGB) {
			convertRGB();
		} else if (typeOriginal == TypeColor.HEX) {
			convertHEX();
		} else if (typeOriginal == TypeColor.HSL) {
			convertHSL();
		}

	}
	private void convertHSL() {
		float new_h = (h / 360.0f);
		float new_s = (s / 100.0f);
		float new_l = (l / 100.0f);

		float new_r,new_g,new_b;
		
		if (new_s == 0) {
			new_r = new_g = new_b = (int) new_l; 
		} else {

			float q = new_l < 0.5 ? new_l * (1 + new_s) : new_l + new_s - new_l * new_s;
			float p = 2 * new_l - q;
			new_r = hue2rgb(p, q, new_h + 1.0f/ 3);
			new_g = hue2rgb(p, q, new_h);
			new_b = hue2rgb(p, q, new_h - 1.0f/ 3);
		}

		r = Math.round(new_r * 255);
		g = Math.round(new_g * 255);
		b = Math.round(new_b * 255);
		hex = String.format("#%02x%02x%02x", r, g, b);
	}
	private static float hue2rgb(float p, float q, float t) {
		if (t < 0)
			t += 1;
		if (t > 1)
			t -= 1;
		if (t < 1.0 / 6)
			return p + (q - p) * 6 * t;
		if (t < 1.0 / 2)
			return q;
		if (t < 2.0 / 3)
			return p + (q - p) * (2.0f / 3 - t) * 6;
		return p;
	}

	private void convertHEX() {
		r = Integer.valueOf(hex.substring(0, 2), 16);
		g = Integer.valueOf(hex.substring(2, 4), 16);
		b = Integer.valueOf(hex.substring(4, 6), 16);
		convertRGB();
	}
	
	private void convertRGB() {
		hex = String.format("#%02x%02x%02x", r, g, b);

		float r = (float) this.r / 255;
		float g = (float) this.g / 255;
		float b = (float) this.b / 255;

		float min = Math.min(r, Math.min(g, b));
		float max = Math.max(r, Math.max(g, b));

		h = 0;

		if (max == min)
			h = 0;
		else if (max == r)
			h = (float) ((60 * (g - b) / (max - min)) + 360) % 360;
		else if (max == g)
			h = (float) (60 * (b - r) / (max - min)) + 120;
		else if (max == b)
			h = (float) (60 * (r - g) / (max - min)) + 240;

		l = (float) (max + min) / 2;

		System.out.println("l: " + l);

		s = 0;

		if (max == min)
			s = 0;
		else if (l <= .5f)
			s = (float) (max - min) / (max + min);
		else
			s = (float) (max - min) / (2 - max - min);

		s *= 100;
		l *= 100;

	}

}
