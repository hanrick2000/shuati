package leetcode;

import java.util.Stack;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_71_Simplify_Path {
    /**
        Given an absolute path for a file (Unix-style), simplify it.

        For example,
        path = "/home/", => "/home"
        path = "/a/./b/../../c/", => "/c"
     */

    public static String simplifyPath(String path) {
        if (null == path || path.length() == 0) return path;

        Stack<String> stack = new Stack<>();
        for (String token : path.split("/")) {
            if (token.equals("") || token.equals(".")) continue;

            //!!! Remember, can't do 'if(!stack.isEmpty() && !stack.isEmpty())", reason:
            //    For case "/../", token is "..", stack is empty, so we should do nothing. But if we use logic "if" logc above,
            //    ".." will be pushed to stack, which is wrong.
            if (token.equals("src")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(token);
            }
        }

        //!!!String.join()
        return "/" + String.join("/", stack);
    }
}
