package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.option.Option;
import ca.genovese.coffeecats.laws.CovariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

@ExtendWith(OptionInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class OptionInstanceTest implements CovariantFunctorLaws<Option> {

}

