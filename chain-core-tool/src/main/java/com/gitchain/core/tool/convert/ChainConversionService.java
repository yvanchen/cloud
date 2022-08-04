package com.gitchain.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author git 
 */
public class ChainConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile ChainConversionService SHARED_INSTANCE;

	public ChainConversionService() {
		this(null);
	}

	public ChainConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link ChainConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code ChainConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		ChainConversionService sharedInstance = ChainConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (ChainConversionService.class) {
				sharedInstance = ChainConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new ChainConversionService();
					ChainConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
