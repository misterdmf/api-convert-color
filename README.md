# api-convert-color
A spring api to convert color. Between the Color Models *RGB, HEX, and HSL.*

## Getting Started

These instructions will get you a way to run and reproduce the sabe API on your local machine for development and testing purposes. 

### How to use

```
Give examples
```

## Do it yourself and have fun! DIY 

### Prerequisites

This is all you need. And 
* A favorite text editor or IDE (In my case, to simplify Eclipse)
* JDK 1.8 or later
* Maven 3.0+

### Create a Java project

```
1. File > New > Java Project
2. Choose a name and click Finish
```

### Convert your project to a Maven Project

```
1. Right-click on your project
2. Configure > Convert to Maven Project
```

### Edit your 'pom.xml' file
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>api-convert-color</groupId> // Change for your project name
	<artifactId>api-convert-color</artifactId> // Change for your project name
	<version>0.0.1-SNAPSHOT</version>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.RELEASE</version>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.jayway.jsonpath</groupId>
			<artifactId>json-path</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<sourceDirectory>src</sourceDirectory>
		<plugins>
			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.7.0</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
			<plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
		</plugins>
	</build>
</project>
```

#### Update project

```
1. Right-click on your project
2. Maven > Update Project
```

### Create a Color model

```
1. Right-click on your project
2. New > Class
3. Type Color as name class
```
*Attention: You have to select a specific package for your class. Do not put it in default package*

```java
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
```

#### Create a enum TypeColor

```
1. Right-click on your project
2. New > Enum
3. Type TypeColor as name enumeration
```

```java
package api;

public enum TypeColor {
	RGB,
	HEX,
	HSL
}

```



### Create a ControllerColor

```
1. Right-click on your project
2. New > Class
3. Type ControllerColor as name class
```

```java

package api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColorController {

	@RequestMapping("/rgb")
	public Color rgb(@RequestParam int r, @RequestParam int g, @RequestParam int b) {
		Color color = new Color(TypeColor.RGB);
		color.setR(r);
		color.setG(g);
		color.setB(b);
		color.convert();
		return color;
	}
	@RequestMapping("/hsl")
	public Color hsl(@RequestParam float h, @RequestParam float s, @RequestParam float l) {
		Color color = new Color(TypeColor.HSL);
		color.setH(h);
		color.setS(s);
		color.setL(l);
		color.convert();
		return color;
	}
	@RequestMapping("/hex")
	public Color hex(@RequestParam String hex) {
		Color color = new Color(TypeColor.HEX);
		color.setHex(hex);
		color.convert();
		return color;
	}
}

```

### Create the main class

```java
package api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

}
```


### To generate the final JAR file:


```
1. Right-click on your project
2. Run As > Maven Install
```

## Deployment

```linux
$java -jar yourProjectName.jar
```


### Enjoy and have fun!

## Author

### Davi Morais Ferreira, Msc.
Software Engineer with over 10 years experience designing and analyzing complex algorithms. Always 
seeking to improve the efficiency of algorithmic solutions. Taught courses in algorithm design and 
analysis, data structure and object‐oriented programming to over 2500 students at a top 10 
university in Brazil. 


## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds
