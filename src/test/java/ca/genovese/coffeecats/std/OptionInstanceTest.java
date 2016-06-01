package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.Option;
import ca.genovese.coffeecats.laws.InvariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(OptionInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class OptionInstanceTest implements InvariantFunctorLaws<Option> {

}

