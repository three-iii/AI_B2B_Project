package com.three_iii.user;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageableAspect {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int[] ALLOWED_PAGE_SIZES = {10, 30, 50};

    @Around("execution(* com.three_iii.user..*(.., org.springframework.data.domain.Pageable, ..))")
    public Object validatePageable(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Pageable) {
                Pageable pageable = (Pageable) args[i];
                int size = DEFAULT_PAGE_SIZE;

                if (Arrays.stream(ALLOWED_PAGE_SIZES).anyMatch(s -> s == pageable.getPageSize())) {
                    size = pageable.getPageSize();
                }

                // 수정된 Pageable로 변경
                args[i] = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
            }
        }
        return joinPoint.proceed(args);
    }
}
