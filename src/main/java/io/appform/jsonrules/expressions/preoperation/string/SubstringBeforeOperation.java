package io.appform.jsonrules.expressions.preoperation.string;

import com.fasterxml.jackson.databind.JsonNode;
import io.appform.jsonrules.ExpressionEvaluationContext;
import io.appform.jsonrules.expressions.preoperation.PreOperation;
import io.appform.jsonrules.expressions.preoperation.PreOperationType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubstringBeforeOperation extends PreOperation<String> {

    private static final String EMPTY_STRING = "";
    @Builder.Default
    private String delimiter;
    private boolean suppressExceptions;

    public SubstringBeforeOperation() {
        super(PreOperationType.sub_str_before);
        this.delimiter = null;
    }

    public SubstringBeforeOperation(String delimiter, boolean suppressExceptions) {
        this();
        this.delimiter = delimiter;
        this.suppressExceptions = suppressExceptions;
    }

    @Override
    public String compute(ExpressionEvaluationContext context) {
        try {
            final JsonNode node = context.getNode();
            if (node.isTextual()) {
                final String nodeText = node.asText(EMPTY_STRING);
                if (delimiter != null) {
                    int index = nodeText.indexOf(delimiter);
                    if (index == -1) {
                        return nodeText;
                    } else {
                        return nodeText.substring(0, index);
                    }
                } else {
                    return nodeText;
                }
            }
            if (suppressExceptions) {
                return EMPTY_STRING;
            }
            throw new IllegalArgumentException("Sub-String-Before operation is not supported");
        } catch (Exception e) {
            if (suppressExceptions) {
                return EMPTY_STRING;
            }
            throw e;
        }
    }
}
