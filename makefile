OUT_DIR=target

clean:
	@find . -name "*.class" -type f -delete
	@rm -rf $(OUT_DIR)

server: clean
	@javac -d $(OUT_DIR) startup/ServerStartup.java

run: server
	@clear
	@java -cp target startup.ServerStartup -p 9876 -r public_html
