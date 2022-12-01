FROM openjdk:17-alpine

ENV JAVA_ENABLE_DEBUG=${JAVA_ENABLE_DEBUG}

COPY build/libs/BESourceryAdmissionTool-*-SNAPSHOT.jar sourcery-admission.jar
COPY entrypoint.sh .

RUN addgroup --system --gid 1001 appuser && \
    adduser --system --ingroup appuser --uid 1001 appuser

RUN chown appuser:appuser sourcery-admission.jar
RUN chmod 500 sourcery-admission.jar
RUN chmod +x entrypoint.sh

EXPOSE 8080

USER 1001

CMD ["./entrypoint.sh"]
