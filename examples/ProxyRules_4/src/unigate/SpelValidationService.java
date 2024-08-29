package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.ExpressionState;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.ast.MethodReference;
import org.springframework.expression.spel.standard.SpelExpression;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SpelValidationService {

    private final ExpressionParser expressionParser;
    private final SpelParserConfiguration spelParserConfiguration;
    private final ProxyRuleContext validationContext;

    public boolean validateExpression(String expression) {
        try {
            return validateExpression(expressionParser.parseExpression(expression));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateExpression(Expression expression) {
        try {
            SpelExpression spelExpression = (SpelExpression) expression;
            List<MethodReference> methodReferenceNodes = getMethodReferenceNodes(spelExpression.getAST());
            ExpressionState expressionState = new ExpressionState(spelExpression.getEvaluationContext(), new TypedValue(validationContext), spelParserConfiguration);

            methodReferenceNodes.forEach(methodReference -> methodReference.getValue(expressionState));
            spelExpression.getValue(validationContext);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private List<MethodReference> getMethodReferenceNodes(SpelNode spelNode) {
        int childCount = spelNode.getChildCount();

        if (childCount <= 0) {
            return Collections.emptyList();
        }

        List<SpelNode> childNodes = IntStream.range(0, childCount)
                .mapToObj(spelNode::getChild)
                .collect(Collectors.toList());

        List<MethodReference> methodReferences = childNodes.stream()
                .filter(MethodReference.class::isInstance)
                .map(MethodReference.class::cast)
                .collect(Collectors.toList());

        childNodes.forEach(node -> methodReferences.addAll(getMethodReferenceNodes(node)));

        return methodReferences;
    }
}
