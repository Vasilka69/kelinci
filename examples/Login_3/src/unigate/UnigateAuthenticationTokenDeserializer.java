package src.unigate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class UnigateAuthenticationTokenDeserializer extends JsonDeserializer<UnigateAuthenticationToken> {
    UnigateAuthenticationTokenDeserializer() {
    }

    public UnigateAuthenticationToken deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        UnigateAuthenticationToken token = null;
        ObjectMapper mapper = (ObjectMapper)jp.getCodec();
        JsonNode jsonNode = (JsonNode)mapper.readTree(jp);
        Boolean authenticated = this.readJsonNode(jsonNode, "authenticated").asBoolean();
        JsonNode principalNode = this.readJsonNode(jsonNode, "principal");
        Object principal = null;
        String redirectUri=this.readJsonNode(jsonNode, "redirectUri").asText();
        if (principalNode.isObject()) {
            principal = mapper.readValue(principalNode.traverse(mapper), Object.class);
        } else {
            principal = principalNode.asText();
        }

        JsonNode credentialsNode = this.readJsonNode(jsonNode, "credentials");
        String credentials;
        if (!credentialsNode.isNull() && !credentialsNode.isMissingNode()) {
            credentials = credentialsNode.asText();
        } else {
            credentials = null;
        }

        List<GrantedAuthority> authorities = mapper.readValue(this.readJsonNode(jsonNode, "authorities").traverse(mapper), new TypeReference<List<GrantedAuthority>>() {});
        if (authenticated) {
            token = new UnigateAuthenticationToken(principal, credentials, authorities, redirectUri);
        } else {
            token = new UnigateAuthenticationToken(principal, credentials, Collections.emptyList(), redirectUri);
        }

        JsonNode detailsNode = this.readJsonNode(jsonNode, "details");
        if (!detailsNode.isNull() && !detailsNode.isMissingNode()) {
            token.setDetails(detailsNode);
        } else {
            token.setDetails((Object)null);
        }

        return token;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return (JsonNode)(jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance());
    }
}

