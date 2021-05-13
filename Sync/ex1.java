static void Main()
{
           bool complete = false;
            var t = new Thread(() =>
            {
                int i=0;
                while (!complete)
                    i++;
                Console.Write(i);
            });
            t.Start();
            Thread.Sleep(1000);
            complete = true;
            t.Join();
}
