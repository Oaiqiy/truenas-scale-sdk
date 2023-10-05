package dev.oaiqiy.truenas.scale.sdk.command;

import dev.oaiqiy.truenas.scale.sdk.service.ITrueNasService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrueNasServicePrefixTree {

    private Map<String, TrueNasServicePrefixTree> children = new HashMap<>();

    private boolean finish = false;

    private ITrueNasService value;


    public void add(String[] commands, ITrueNasService value) {
        add(commands, 0, value);
    }

    private void add(String[] commands, int idx, ITrueNasService value) {
        if (commands.length == idx) {
            this.value = value;
            this.finish = true;
            return;
        }
        TrueNasServicePrefixTree child = children.getOrDefault(commands[idx], new TrueNasServicePrefixTree());
        if (child.isFinish()) {
            throw new RuntimeException("duplicate");
        }
        children.put(commands[idx], child);
        child.add(commands, ++idx, value);
    }


    public ITrueNasService find(String[] commands) {
        if (commands == null || commands.length == 0) {
            return null;
        }
        return find(commands, 0);
    }

    private ITrueNasService find(String[] commands, int idx) {
        if (commands.length == idx) {
            if (finish) {
                return value;
            } else {
                return null;
            }
        }
        String key = commands[idx];
        if (!children.containsKey(key)) {
            return null;
        }
        return children.get(key).find(commands, ++idx);
    }

    public Map<String, Object> execute(String[] commands) {
        return execute(commands, 0);
    }

    private Map<String, Object> execute(String[] commands, int idx) {
        if (finish) {
            return value.execute(Arrays.copyOfRange(commands, idx, commands.length));
        }

        String key = commands[idx];
        if (!children.containsKey(key)) {
            return null;
        }
        return children.get(key).execute(commands, ++idx);
    }
}
