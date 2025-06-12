package com.xpp.gaia.mybatis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * Mybatis Invocation包装类
 *
 * @author Akira
 * @since 2022/2/18
 */
@Slf4j
public class MybatisInvocationWrapper {

    private SqlCommandType sqlType;
    @Getter
    private Invocation invocation;
    @Getter
    private MappedStatement mappedStatement;
    @Getter
    private Object parameterObject;
    @Getter
    private BoundSql boundSql;
    @Getter
    private Executor executor;
    @Getter
    private CacheKey cacheKey;
    @Getter
    private net.sf.jsqlparser.statement.Statement statement;
    @Getter
    private RowBounds rowBounds;
    @Getter
    private ResultHandler resultHandler;

    public MybatisInvocationWrapper buildWrapper(Invocation invocation) throws Throwable {
        this.invocation = invocation;
        this.executor = (Executor) invocation.getTarget();
        Object[] args = invocation.getArgs();
        this.mappedStatement = (MappedStatement) args[0];
        this.parameterObject = args[1];
        this.sqlType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.SELECT.equals(this.sqlType)) {
            this.buildQueryWrapper(invocation);
        } else {
            this.buildUpdateWrapper(invocation);
        }
        return this;
    }

    private void buildQueryWrapper(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        this.rowBounds = (RowBounds) args[2];
        this.resultHandler = (ResultHandler) args[3];
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            this.boundSql = this.mappedStatement.getBoundSql(this.parameterObject);
            this.cacheKey = executor.createCacheKey(this.mappedStatement, this.parameterObject, rowBounds, this.boundSql);
        } else {
            //6 个参数时
            this.cacheKey = (CacheKey) args[4];
            this.boundSql = (BoundSql) args[5];
        }
    }

    private void buildUpdateWrapper(Invocation invocation) throws Throwable {
        this.boundSql = this.mappedStatement.getBoundSql(this.parameterObject);
        try {
            this.statement = CCJSqlParserUtil.parse(this.boundSql.getSql());
        } catch (Exception e) {
            log.warn("框架发现可能为批处理的Sql，将无法进行自动填充");
            this.statement = null;
        }
    }

    public Object execute() throws Throwable {
        if (SqlCommandType.SELECT.equals(this.sqlType)) {
            return executor.query(this.mappedStatement,
                    this.parameterObject,
                    this.rowBounds,
                    this.resultHandler,
                    this.cacheKey,
                    this.boundSql);
        } else {
            return executor.update(this.mappedStatement, this.parameterObject);
        }
    }

    public void rebuildMappedStatement(String sql) {
        MappedStatement mappedStatement = this.getMappedStatement();
        Configuration configuration = mappedStatement.getConfiguration();
        BoundSql newBoundSql = new BoundSql(configuration,
                sql,
                this.getBoundSql().getParameterMappings(),
                this.getBoundSql().getParameterObject());
        StaticSqlSource newSqlSource = new StaticSqlSource(configuration, newBoundSql.getSql(), newBoundSql.getParameterMappings());
        MappedStatement newMappedStatement = new MappedStatement.Builder(mappedStatement.getConfiguration(),
                mappedStatement.getId(),
                newSqlSource,
                mappedStatement.getSqlCommandType())
                .resource(mappedStatement.getResource())
                .parameterMap(mappedStatement.getParameterMap())
                .flushCacheRequired(mappedStatement.isFlushCacheRequired())
                .keyGenerator(mappedStatement.getKeyGenerator())
                .keyProperty(this.zplit(mappedStatement.getKeyProperties()))
                .keyColumn(this.zplit(mappedStatement.getKeyColumns()))
                .timeout(mappedStatement.getTimeout())
                .cache(mappedStatement.getCache())
                .useCache(mappedStatement.isUseCache())
                .databaseId(mappedStatement.getDatabaseId())
                .fetchSize(mappedStatement.getFetchSize())
                .lang(mappedStatement.getLang())
                .resultMaps(mappedStatement.getResultMaps())
                .resultOrdered(mappedStatement.isResultOrdered())
                .build();
        this.boundSql = newBoundSql;
        this.mappedStatement = newMappedStatement;
    }

    private String zplit(String[] strings) {
        if (strings == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str + ",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }
}
