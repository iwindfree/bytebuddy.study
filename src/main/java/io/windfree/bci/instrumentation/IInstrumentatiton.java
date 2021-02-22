package io.windfree.bci.instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

public interface IInstrumentatiton {
    void transform(AgentBuilder agentBuilder, Instrumentation inst);
}
