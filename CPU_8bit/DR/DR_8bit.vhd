library ieee;
use ieee.std_logic_1164.all;
entity DR_8bit is
port (clk:in std_logic;
			D:in std_logic_vector(7 downto 0);
		cin,oin:in std_logic;
		RST:in std_logic;
		   Q:out std_logic_vector(7 downto 0);
		cout,ov:out std_logic
);
end DR_8bit;

architecture bhv of DR_8bit is
signal Q1:std_logic_vector(7 downto 0);
signal co:std_logic;
begin
process(clk,Q1,co,rst)
begin
	if rst='1' then
		Q1<=(others=>'0');
		co<='0';
		ov<='0';
	elsif clk'event and clk='1' then 
		Q1<=D;
		co<=cin;
		ov<=oin;
	end if;
end process;
Q<=Q1;
cout<=co;
end bhv;