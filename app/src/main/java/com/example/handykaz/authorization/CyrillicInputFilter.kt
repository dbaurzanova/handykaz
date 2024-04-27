import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class CyrillicInputFilter : InputFilter {
    private val cyrillicPattern: Pattern = Pattern.compile("[а-яА-Я]*")

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val input = source.toString()
        return if (cyrillicPattern.matcher(input).matches()) {
            null  // Allow input
        } else {
            source?.filter { it.isLetterOrDigit() } // Allow only letters and digits
        }
    }
}
