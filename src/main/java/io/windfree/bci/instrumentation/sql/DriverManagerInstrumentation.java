package io.windfree.bci.instrumentation.sql;

import io.windfree.bci.instrumentation.IInstrumentatiton;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.method.ParameterList;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class DriverManagerInstrumentation implements IInstrumentatiton {
    public AgentBuilder instrument(AgentBuilder agentBuilder) {
        return AdviceRegistry.subTypesOf(HttpServlet.class)
                .advice((method) -> {
                    ParameterList<ParameterDescription.InDefinedShape> parameters = method.getParameters();
                    return method.getInternalName().equals("service")
                            && parameters.size() == 2
                            && parameters.get(0).getType().isAssignableTo(HttpServletRequest.class)
                            && parameters.get(1).getType().isAssignableTo(HttpServletResponse.class);
                }, ServletAdvice.class).register(agentBuilder);
    }



    public void transform(AgentBuilder agentBuilder, Instrumentation inst) {
        agentBuilder.type(named("io.windfree.app.test1"))
                .transform(new AgentBuilder.Transformer.ForAdvice()
                .advice(
                        ElementMatchers.named()
                ));
    }

    private static class ServletAdvice {
        @Advice.OnMethodEnter
        private static void enter(@Advice.Argument(0) HttpServletRequest request, @Tag int tag) {
            String method = request.getMethod();
            String requestUri = request.getRequestURI();
            String traceId = request.getHeader(TracingHeaders.TRACE_ID);
            Callbacks.find(ServletInstrumentation.class).call(1, method, requestUri, traceId);
        }

        @Advice.OnMethodExit
        private static void exit(@Advice.Argument(1) HttpServletResponse response) {
            Callbacks.find(ServletInstrumentation.class).call(2, response);
        }
    }
}
