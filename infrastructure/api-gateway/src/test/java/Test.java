import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年08月06日
 */
public class Test {
    public static void main(String[] args) {
        String str = "762.31,661.04,44.19,335.69,15.15,55.69,7500,1093.38,1100,200";
        String[] split = str.split(",");
        BigDecimal collect = Arrays.stream(split).map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(collect);
    }
}
