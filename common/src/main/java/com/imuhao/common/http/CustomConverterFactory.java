package com.imuhao.common.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.imuhao.common.utils.AESCrypt;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by dafan on 2016/11/12 0012.
 */

public class CustomConverterFactory extends Converter.Factory {
	public static CustomConverterFactory create() {
		return create(new Gson());
	}

	public static CustomConverterFactory create(Gson gson) {
		return new CustomConverterFactory(gson);
	}

	private final Gson gson;

	private CustomConverterFactory(Gson gson) {
		if (gson == null) throw new NullPointerException("gson == null");
		this.gson = gson;
	}

	/**
	 * 请求
	 *
	 * @param type
	 * @param parameterAnnotations
	 * @param methodAnnotations
	 * @param retrofit
	 * @return
	 */
	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new DecodeRequestBodyConverter<>(gson, adapter);
	}

	/**
	 * 响应
	 *
	 * @param type
	 * @param annotations
	 * @param retrofit
	 * @return
	 */
	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new DecodeResponseBodyConverter<>(adapter);
	}

	/**
	 * 加密请求
	 *
	 * @param <T>
	 */
	class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
		MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
		Charset UTF_8 = Charset.forName("UTF-8");

		private final Gson gson;
		private final TypeAdapter<T> adapter;

		DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.gson = gson;
			this.adapter = adapter;
		}

		@Override
		public RequestBody convert(T value) throws IOException {
			Buffer buffer = new Buffer();
			Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
			JsonWriter jsonWriter = gson.newJsonWriter(writer);
			adapter.write(jsonWriter, value);
			jsonWriter.flush();
			return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
		}
	}

	/**
	 * 解密结果
	 *
	 * @param <T>
	 */
	class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
		private final TypeAdapter<T> adapter;

		DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
			this.adapter = adapter;
		}

		@Override
		public T convert(ResponseBody value) throws IOException {
			String result = value.string();
			try {
				result = new AESCrypt().decrypt(result);
				result = result.replaceAll("\n", "");
			} catch (Exception e) {
				e.printStackTrace();
				value.close();
			}
			return adapter.fromJson(result);
		}
	}
}
