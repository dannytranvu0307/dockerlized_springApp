# Sử dụng hình ảnh Maven chính thức của Apache để xây dựng ứng dụng
FROM maven:3.8.3-openjdk-17-slim AS build

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép các tệp POM và mã nguồn vào container
COPY . /app

# Biên dịch và đóng gói ứng dụng
RUN mvn clean package -DskipTests

# Sử dụng hình ảnh JDK 17 chính thức của OpenJDK làm nền tảng để chạy ứng dụng
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR từ giai đoạn build sang container
COPY --from=build /app/target/docker-spring-0.0.1-SNAPSHOT.jar /app/app.jar

# Thay đổi quyền sở hữu và cấp quyền thực thi cho tệp JAR
RUN chmod +x /app/app.jar

# Cấu hình điểm vào cho container
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
