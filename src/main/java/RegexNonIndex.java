public class RegexNonIndex extends NonIndex {
    @Override
    protected boolean matcher(String toFind, String s) {
        return s.matches(".*" + toFind + ".*");
    }
}
