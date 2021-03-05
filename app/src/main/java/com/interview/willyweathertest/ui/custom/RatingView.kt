package com.interview.willyweathertest.ui.custom

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import java.text.DecimalFormat
import com.interview.willyweathertest.R
class RatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paint: Paint? = null
    private var bgPaint: Paint? = null
    private var finishedPaint: Paint? = null
    protected var textPaint: Paint? = null

    private val rectF = RectF()

    private var strokeWidth = 0f
    private var finishedStrokeWidth = 0f

    private var showProgressText = false
    private var suffixTextSize = 0f
    private var bottomTextSize = 0f
    private var bottomText: String? = null
    private var textSize = 0f
    private var textColor = 0
    private var progress = 0f
    private var max = 0
    private var finishedStrokeColor = 0
    private var unfinishedStrokeColor = 0
    private var bgColor = 0
    private var arcAngle = 360f
    private var suffixText = "%"
    private var suffixTextPadding = 0f
    private var circularPadding = 0f
    private var arcBottomHeight = 0f

    private val default_finished_color: Int = Color.WHITE
    private val default_unfinished_color: Int = Color.rgb(72, 106, 176)
    private val default_text_color: Int = Color.rgb(66, 145, 241)
    private var default_suffix_text_size = 0f
    private var default_suffix_padding = 0f
    private var default_bottom_text_size = 0f
    private var default_stroke_width = 0f
    private var default_suffix_text: String? = null
    private val default_max = 100
    private val default_arc_angle = 360 * 0.8f
    private var default_text_size = 0f
    private var min_size = 0

    private val INSTANCE_STATE = "saved_instance"
    private val INSTANCE_STROKE_WIDTH = "stroke_width"
    private val INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width"
    private val INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size"
    private val INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding"
    private val INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size"
    private val INSTANCE_BOTTOM_TEXT = "bottom_text"
    private val INSTANCE_TEXT_SIZE = "text_size"
    private val INSTANCE_TEXT_COLOR = "text_color"
    private val INSTANCE_PROGRESS = "progress"
    private val INSTANCE_MAX = "max"
    private val INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color"
    private val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
    private val INSTANCE_ARC_ANGLE = "rv_angle"
    private val INSTANCE_SUFFIX = "suffix"
    private val INSTANCE_BG_COLOR = "bg_color"
    private val INSTANCE_CIRCULAR_PADDING = "circular_padding"
    private val INSTANCE_FINISHED_COLOR_RANGES = "finished_color_ranges"
    private val INSTANCE_UNFINISHED_COLOR_RANGES = "rv_unfinished_color_ranges"
    private val INSTANCE_PROGRESS_RANGES = "progress_ranges"
    private var startAngle = 270f
    private lateinit var finishedColorAr: IntArray
    private lateinit var unfinishedColorAr: IntArray
    private lateinit var progressRangesAr: IntArray

    init {
        default_text_size = sp2px(resources, 18f)
        min_size = dp2px(resources, 100f).toInt()
        default_text_size = sp2px(resources, 40f)
        default_suffix_text_size = sp2px(resources, 15f)
        default_suffix_padding = dp2px(resources, 4f)
        default_suffix_text = "%"
        default_bottom_text_size = sp2px(resources, 10f)
        default_stroke_width = dp2px(resources, 4f)

        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RatingView,
            defStyleAttr,
            0
        )

        initByAttributes(attributes)
        attributes.recycle()
        initPainters()

    }

    fun dp2px(resources: Resources, dp: Float): Float {
        val scale: Float = resources.getDisplayMetrics().density
        return dp * scale + 0.5f
    }

    fun sp2px(resources: Resources, sp: Float): Float {
        val scale: Float = resources.getDisplayMetrics().scaledDensity
        return sp * scale
    }

    protected fun initByAttributes(attributes: TypedArray) {
        finishedStrokeColor =
            attributes.getColor(R.styleable.RatingView_rv_finished_color, default_finished_color)
        unfinishedStrokeColor = attributes.getColor(
            R.styleable.RatingView_rv_unfinished_color,
            default_unfinished_color
        )
        textColor = attributes.getColor(R.styleable.RatingView_rv_text_color, default_text_color)
        textSize = attributes.getDimension(R.styleable.RatingView_rv_text_size, default_text_size)
        arcAngle = attributes.getFloat(R.styleable.RatingView_rv_angle, default_arc_angle)
        startAngle = attributes.getFloat(R.styleable.RatingView_rv_start_angle, 270f)
        setMax(attributes.getInt(R.styleable.RatingView_rv_max, default_max))
        setProgress(attributes.getFloat(R.styleable.RatingView_rv_progress, 0f))
        strokeWidth =
            attributes.getDimension(R.styleable.RatingView_rv_stroke_width, default_stroke_width)
        finishedStrokeWidth = attributes.getDimension(
            R.styleable.RatingView_rv_finished_stroke_width,
            default_stroke_width
        )
        showProgressText =
            attributes.getBoolean(R.styleable.RatingView_rv_show_progress_text, true)
        suffixTextSize = attributes.getDimension(
            R.styleable.RatingView_rv_suffix_text_size,
            default_suffix_text_size
        )
        suffixText =
            if (TextUtils.isEmpty(attributes.getString(R.styleable.RatingView_rv_suffix_text)))
                default_suffix_text!!
            else attributes.getString(
                R.styleable.RatingView_rv_suffix_text
            )!!

        suffixTextPadding = attributes.getDimension(
            R.styleable.RatingView_rv_suffix_text_padding,
            default_suffix_padding
        )
        bottomTextSize = attributes.getDimension(
            R.styleable.RatingView_rv_bottom_text_size,
            default_bottom_text_size
        )
        bottomText = attributes.getString(R.styleable.RatingView_rv_bottom_text)

        bgColor = attributes.getColor(R.styleable.RatingView_rv_bg_color, 0)
        circularPadding = attributes.getDimension(R.styleable.RatingView_rv_circular_padding, 0f)


        val unfinishedResourceId = attributes.getResourceId(
            R.styleable.RatingView_rv_unfinished_color_ranges,
            0
        )

        if (unfinishedResourceId != 0) {
            unfinishedColorAr = attributes.resources.getIntArray(unfinishedResourceId)
        }

        val progressRangesId = attributes.getResourceId(
            R.styleable.RatingView_rv_progress_ranges,
            0
        )

        if (progressRangesId != 0) {
            progressRangesAr = attributes.resources.getIntArray(progressRangesId)
        }

        val finishedResourceId = attributes.getResourceId(
            R.styleable.RatingView_rv_finished_color_ranges,
            0
        )
        if (finishedResourceId != 0) {
            finishedColorAr = attributes.resources.getIntArray(finishedResourceId)
        }
    }

    protected fun initPainters() {
        textPaint = TextPaint().also {
            it.color = textColor
            it.textSize = textSize
            it.isAntiAlias = true
        }
        paint = Paint().also {
            it.color = default_unfinished_color
            it.isAntiAlias = true
            it.strokeWidth = strokeWidth
            it.style = Paint.Style.STROKE
            it.strokeCap = Paint.Cap.SQUARE
        }
        finishedPaint = Paint().also {
            it.color = default_finished_color
            it.isAntiAlias = true
            it.strokeWidth = finishedStrokeWidth
            it.style = Paint.Style.STROKE
            it.strokeCap = Paint.Cap.ROUND
        }
        bgPaint = Paint().also {
            it.color = bgColor
            it.style = Paint.Style.FILL
        }
    }

    open fun getStrokeWidth(): Float = strokeWidth

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        postInvalidate()
    }

    fun getSuffixTextSize(): Float = suffixTextSize

    fun setSuffixTextSize(suffixTextSize: Float) {
        this.suffixTextSize = suffixTextSize
        postInvalidate()
    }

    fun getBottomText(): String? = bottomText


    fun setBottomText(bottomText: String?) {
        this.bottomText = bottomText
        postInvalidate()
    }

    fun getProgress(): Float = progress


    fun setProgress(progress: Float) {
        this.progress = java.lang.Float.valueOf(DecimalFormat("#").format(progress))
        if (this.progress > getMax()) {
            this.progress %= getMax().toFloat()
        }
        postInvalidate()
    }

    fun getMax(): Int = max

    fun setMax(max: Int) {
        if (max >= 0) {
            this.max = max
            postInvalidate()
        }
    }

    fun getBottomTextSize(): Float = bottomTextSize


    fun setBottomTextSize(bottomTextSize: Float) {
        this.bottomTextSize = bottomTextSize
        postInvalidate()
    }

    fun getTextSize(): Float = textSize

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        postInvalidate()
    }

    fun getTextColor(): Int = textColor

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        postInvalidate()
    }

    fun getFinishedStrokeColor(): Int = finishedStrokeColor


    fun setFinishedStrokeColor(finishedStrokeColor: Int) {
        this.finishedStrokeColor = finishedStrokeColor
        postInvalidate()
    }

    fun getBgColor(): Int = bgColor


    fun setBgColor(bgColor: Int) {
        this.bgColor = bgColor
    }

    fun getUnfinishedStrokeColor(): Int = unfinishedStrokeColor

    fun setUnfinishedStrokeColor(unfinishedStrokeColor: Int) {
        this.unfinishedStrokeColor = unfinishedStrokeColor
        postInvalidate()
    }

    fun getArcAngle(): Float = arcAngle

    fun setArcAngle(arcAngle: Float) {
        this.arcAngle = arcAngle
        postInvalidate()
    }

    fun getSuffixText(): String? = suffixText

    fun setSuffixText(suffixText: String?) {
        this.suffixText = suffixText!!
        postInvalidate()
    }

    fun getSuffixTextPadding(): Float = suffixTextPadding

    fun setSuffixTextPadding(suffixTextPadding: Float) {
        this.suffixTextPadding = suffixTextPadding
        postInvalidate()
    }

    override fun getSuggestedMinimumHeight(): Int = min_size

    override fun getSuggestedMinimumWidth(): Int = min_size


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var rectWidth = strokeWidth
        if (finishedStrokeWidth > strokeWidth) {
            rectWidth = finishedStrokeWidth
        }

        rectF.set(
            circularPadding + (rectWidth / 2f),
            circularPadding + (rectWidth / 2f),
            width - circularPadding - rectWidth / 2f,
            (MeasureSpec.getSize(heightMeasureSpec) - circularPadding - rectWidth / 2f)
        )
        val radius = width / 2f
        val angle = (360 - arcAngle) / 2f
        arcBottomHeight = radius * (1 - Math.cos(angle / 180 * Math.PI)).toFloat()
    }


    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putFloat(INSTANCE_STROKE_WIDTH, strokeWidth)
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, finishedStrokeWidth)
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, suffixTextSize)
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, suffixTextPadding)
        bundle.putFloat(INSTANCE_BOTTOM_TEXT_SIZE, bottomTextSize)
        bundle.putString(INSTANCE_BOTTOM_TEXT, bottomText)
        bundle.putFloat(INSTANCE_TEXT_SIZE, textSize)
        bundle.putInt(INSTANCE_TEXT_COLOR, textColor)
        bundle.putFloat(INSTANCE_PROGRESS, progress)
        bundle.putInt(INSTANCE_MAX, max)
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, finishedStrokeColor)
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, unfinishedStrokeColor)
        bundle.putFloat(INSTANCE_ARC_ANGLE, arcAngle)
        bundle.putString(INSTANCE_SUFFIX, suffixText)
        bundle.putInt(INSTANCE_BG_COLOR, bgColor)
        bundle.putFloat(INSTANCE_CIRCULAR_PADDING, circularPadding)
        bundle.putIntArray(INSTANCE_FINISHED_COLOR_RANGES, finishedColorAr)
        bundle.putIntArray(INSTANCE_UNFINISHED_COLOR_RANGES, unfinishedColorAr)
        bundle.putIntArray(INSTANCE_PROGRESS_RANGES, progressRangesAr)
        return bundle
    }

    private fun getfinishedStrokeWidth(): Float = finishedStrokeWidth

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val bundle = state
            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH)
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH)
            suffixTextSize = bundle.getFloat(INSTANCE_SUFFIX_TEXT_SIZE)
            suffixTextPadding = bundle.getFloat(INSTANCE_SUFFIX_TEXT_PADDING)
            circularPadding = bundle.getFloat(INSTANCE_CIRCULAR_PADDING)
            bottomTextSize = bundle.getFloat(INSTANCE_BOTTOM_TEXT_SIZE)
            bottomText = bundle.getString(INSTANCE_BOTTOM_TEXT)
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE)
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR)
            setMax(bundle.getInt(INSTANCE_MAX))
            setProgress(bundle.getFloat(INSTANCE_PROGRESS))
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR)
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR)


            finishedColorAr = bundle.getIntArray(INSTANCE_FINISHED_COLOR_RANGES)!!
            unfinishedColorAr = bundle.getIntArray(INSTANCE_UNFINISHED_COLOR_RANGES)!!
            progressRangesAr = bundle.getIntArray(INSTANCE_PROGRESS_RANGES)!!

            suffixText = bundle.getString(INSTANCE_SUFFIX)!!
            bgColor = bundle.getInt(INSTANCE_BG_COLOR)
            initPainters()
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        try {

            for (i in 0..progressRangesAr.size) {
                if (progress >= progressRangesAr[i]) {
                    finishedStrokeColor = finishedColorAr[i]
                    unfinishedStrokeColor = unfinishedColorAr[i]
                    break
                }
            }
        } catch (e: Exception) {

        }

        bgPaint?.let {
            it.color = bgColor
            it.style = Paint.Style.FILL
        }
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, bgPaint!!)

        //float startAngle = 270 - arcAngle / 2f;
        val finishedSweepAngle = progress / getMax().toFloat() * arcAngle
        var finishedStartAngle = startAngle

        if (progress == 0f) finishedStartAngle = 0.01f
        paint!!.color = unfinishedStrokeColor

        canvas.drawArc(rectF, startAngle, arcAngle, false, paint!!)

        finishedPaint!!.strokeWidth = finishedStrokeWidth
        finishedPaint!!.color = finishedStrokeColor

        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, finishedPaint!!)

        if (showProgressText) {
            val text = progress.toInt().toString()
            if (!TextUtils.isEmpty(text)) {
                textPaint!!.color = textColor
                textPaint!!.textSize = textSize

                val textHeight = textPaint!!.descent() + textPaint!!.ascent()
                val textBaseline = (height - textHeight) / 2.0f

                canvas.drawText(
                    text,
                    (width - textPaint!!.measureText(text)) / 2.0f,
                    textBaseline,
                    textPaint!!
                )

                var textBounds = Rect()
                textPaint!!.getTextBounds(text, 0, text.length, textBounds)
                val suffixX =
                    (width - textPaint!!.measureText(text)) / 2.0f + textBounds.width() + dp2px(
                        resources,
                        2f
                    )

                textPaint!!.textSize = suffixTextSize
                val suffixHeight = textPaint!!.descent() + textPaint!!.ascent()

                canvas.drawText(
                    suffixText,
                    suffixX,
                    textBaseline + textHeight - suffixHeight,
                    textPaint!!
                )

            }
        }
        if (arcBottomHeight == 0f) {
            val radius = width / 2f
            val angle = (360 - arcAngle) / 2f
            arcBottomHeight = radius * (1 - Math.cos(angle / 180 * Math.PI)).toFloat()
        }

        bottomText?.let {
            textPaint!!.textSize = bottomTextSize
            val bottomTextBaseline =
                height - arcBottomHeight - (textPaint!!.descent() + textPaint!!.ascent()) / 2
            canvas.drawText(
                it,
                (width - textPaint!!.measureText(it)) / 2.0f,
                bottomTextBaseline,
                textPaint!!
            )
        }
    }
}