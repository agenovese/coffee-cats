package ca.genovese.coffeecats.std;

import ca.genovese.coffeecats.data.eval.Eval;
import ca.genovese.coffeecats.laws.CovariantFunctorLaws;
import org.junit.gen5.api.extension.ExtendWith;

/**
 * Test that the EvalInstance follows the CovariantFunctorLaws.
 */
@ExtendWith(EvalInstanceProvider.class)
@ExtendWith(FunctionProvider.class)
public class EvalInstanceTest implements CovariantFunctorLaws<Eval> {

}

