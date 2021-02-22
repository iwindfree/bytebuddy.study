package io.windfree.agent;

import io.windfree.bci.instrumentation.IInstrumentatiton;
import io.windfree.bci.instrumentation.sql.DriverManagerInstrumentation;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;

public class AgentTransformer  {
    AgentBuilder agentBuilder;
    Instrumentation instrumentation;
    List<IInstrumentatiton> instrumentatitons = null;

    public AgentTransformer(AgentBuilder builder, Instrumentation inst) {
        this.agentBuilder = builder;
        this.instrumentation = inst;
        instrumentatitons = new ArrayList<IInstrumentatiton>();
        instrumentatitons.add(new DriverManagerInstrumentation());
    }

    public void transform() {
        for(IInstrumentatiton inst : instrumentatitons) {
            inst.transform(agentBuilder,instrumentation);
        }
    }

}
