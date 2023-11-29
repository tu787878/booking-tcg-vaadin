package de.tcg.booking.util;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class NumbericConverter {

	public class FloatDoubleConverter implements Converter<Double, Float> {

		private static final long serialVersionUID = 1L;
		
		public FloatDoubleConverter() {
			
		}

		@Override
		public Result<Float> convertToModel(Double value, ValueContext context) {
			return Result.ok(Float.valueOf(value.floatValue()));
		}

		@Override
		public Double convertToPresentation(Float value, ValueContext context) {
			return Double.valueOf(value);
		}

	}

}
