package io.windfree.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class JavaAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        AgentBuilder agent = new AgentBuilder.Default();


    }
}
