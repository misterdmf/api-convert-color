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
}
